package ru.skblab.camundacli.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;
import ru.skblab.camundacli.ApplicationProperties;
import ru.skblab.camundacli.common.Utils;
import ru.skblab.camundacli.core.FileSystemManager;
import ru.skblab.camundacli.core.ShellHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@ShellComponent
@ShellCommandGroup("Справочные команды по состоянию контекста пользователя.")
@AllArgsConstructor
public class CliHelper {

    private static final String UNDEFINED_MARK = "UNDEFINED";

    private final ApplicationProperties parameters;
    private final FileSystemManager fileSystemManager;
    private final ShellHelper shellHelper;

    @ShellMethod(value = "Список поддерживаемых проектов.", key = "l")
    public void listProject() {
        if (getListProjects().size() > 0) {
            shellHelper.printSuccess("Список поддерживаемых проектов:");
            TableModel model = new ArrayTableModel(Utils.getArrayData(getListProjects()));
            TableBuilder builder = new TableBuilder(model);
            builder.addFullBorder(BorderStyle.fancy_light_quadruple_dash);
            shellHelper.printSuccess(builder.build().render(50));
        } else {
            shellHelper.printError("Список проектов пуст !");
        }
    }

    @ShellMethod(value = "Список загруженных системных параметров из клнфигурационного файла.", key = "sp")
    public void listSystemParam() throws FileNotFoundException {
        listProject();

        printActiveProject();
        printActivePath();
        printActiveProjectUrl();
        printDeploymentId();
        shellHelper.printInfo("Путь к папке, в которой хранить скачанные проекты - " + parameters.getProjectsDir());
        shellHelper.printInfo("Эл. почта - " + parameters.getEmail());
        shellHelper.printInfo("Имя пользователя для гита - " + parameters.getUsername());
        shellHelper.printInfo("Ссылка на гитлаб для извлечения проектов - " + parameters.getUrl());
    }

    private void printDeploymentId() throws FileNotFoundException{
        String activeProject = parameters.getActiveProject();
        String deploymentId = activeProject == null ? UNDEFINED_MARK : fileSystemManager.getDefaultDeploymentId(activeProject);
        shellHelper.printInfo("Идентификатор для деплоя из текущего активного проекта - " + deploymentId);
    }

    private void printActiveProjectUrl() {
        String activeProject = parameters.getActiveProject();
        String url = parameters.getUriGitActiveProject();
        shellHelper.printInfo("Ссылка в гите на текущий активный проект - " + (activeProject == null ? UNDEFINED_MARK : url));
    }

    private void printActivePath() {
        String activeProject = parameters.getActiveProject();
        File path = parameters.getPathActiveProject();
        shellHelper.printInfo("Путь к текущему активному проекту - " + (activeProject == null ? UNDEFINED_MARK : path));
    }

    private void printActiveProject() {
        String activeProject = parameters.getActiveProject();
        shellHelper.printInfo("Текущий активный проект - " + (activeProject == null ? UNDEFINED_MARK : activeProject));
    }

    private Map<String, String> getListProjects() {
        return parameters.getProjects();
    }
}
