package ru.skblab.camundacli.shell;

import lombok.AllArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.shell.standard.*;
import ru.skblab.camundacli.ApplicationProperties;
import ru.skblab.camundacli.core.FileSystemManager;
import ru.skblab.camundacli.core.ShellHelper;
import ru.skblab.camundacli.services.CamundaDeployerService;
import ru.skblab.camundacli.services.GitCommandService;

import java.io.IOException;
import java.net.URISyntaxException;

@ShellComponent
@ShellCommandGroup("Команды управления проектами.")
@AllArgsConstructor
public class ProjectManagement {

    private final FileSystemManager fileSystemManager;
    private final ApplicationProperties applicationProperties;
    private final GitCommandService gitCommandService;
    private final CamundaDeployerService camundaDeployerService;
    private final ShellHelper shellHelper;

    @ShellMethod(value = "Установка активного проекта и получение его из gitlab.", key = "ps")
    public void selectActiveProject(
            @ShellOption(value = {"-p", "--project"}, defaultValue = "",
                    help = "Выбор активного проекта, по умолчанию первый в списке.") String project,
            @ShellOption(value = {"-r", "--refresh"}, defaultValue = "true",
                    help = "Удалить локальный проект и взять в git новый, по умолчанию просто переключится на существующий.") Boolean refresh) throws URISyntaxException, GitAPIException {

        if (!checkProject(project)) {
            shellHelper.printError("Проект " + project + " отсутсвует в списке проектов, выберете корретный проект.(Показать список доступных проектов - l)");
            return;
        }
        applicationProperties.setActiveProject(project);

        if (!refresh)
            return;

        if (fileSystemManager.deleteProjectDir(project)) {
            gitCommandService.gitCloneProject(project);
            shellHelper.printSuccess("Проект скачан/обновлен успешно.");
        } else {
            shellHelper.printError("Не удалось удалить каталог с проектом, удалите его самостоятельно.");
        }

    }

    @ShellMethodAvailability("checkPushAndDeploy")
    @ShellMethod(value = "Сохранить и передать изменения во внешнее хранилище(gitlab) и задеплоить в камунду.", key = "pp")
    public void pushProject(
            @ShellOption(value = {"-p", "--project"}, defaultValue = "",
                    help = "Залить указанный проект в gitlab, если найден.") String project) throws URISyntaxException {
        if (!checkProject(project)) {
            shellHelper.printError("Выбран несуществующий проект! Повторите команду.(Показать список доступных проектов - l)");
            return;
        }
        if (!fileSystemManager.checkBaseProjectDir(project)) {
            shellHelper.printError("Отсутсвует каталог с указанным проектом, выберете корретный проект.(Показать список доступных проектов - l)");
            return;
        }

        applicationProperties.setActiveProject(project);
        try {

            String commitMessage = shellHelper.ask("Ваш комменарий к заливаемым изменениям >");
            gitCommandService.gitCommitAndPushProject(project, applicationProperties.getUsername() + ": " + commitMessage);
            shellHelper.printSuccess("Проект успешно обновлен в gitlab.");
            camundaDeployerService.deployProjectPackage(project);
            shellHelper.printSuccess("Проект успешно задеплоен в camunda.");
        } catch (IOException | GitAPIException e) {
            shellHelper.printError("Сохранить проект в gitlab не удалось, убедитесь что правильно задан логин/пароль и у вас есть доступ к обновлению проекта в gitlab.");
        }
        shellHelper.printSuccess("Проект залит/обновлен успешно.");
    }

    private Boolean checkProject(String project) {
        return applicationProperties.getProjects().containsKey(project);
    }
}
