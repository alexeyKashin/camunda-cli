package ru.skblab.camundacli.shell;

import lombok.AllArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.skblab.camundacli.ApplicationProperties;
import ru.skblab.camundacli.services.CamundaDeployerService;
import ru.skblab.camundacli.services.GitCommandService;

import java.io.File;
import java.io.IOException;

@ShellComponent
@ShellCommandGroup("Команды работы с git напрямую.")
@AllArgsConstructor
public class GitCommandShell {

    private final GitCommandService gitCommandService;
    private final ApplicationProperties properties;

    @ShellMethod(value = "Забрать указанный проект из gitlab.", key = "gc")
    public void gitClone(
            @ShellOption(value = {"-u", "--uri"}, defaultValue = "https://gitlab.skblab.ru/camunda_processes/fssp.git",
                    help = "Ссылка на проект в gitlab.") String uri,
            @ShellOption(value = {"-p", "--path"}, defaultValue = "c:/temp/",
                    help = "Путь в каталог КУДА клонировать проект.") File path) throws GitAPIException {
        gitCommandService.gitClone(uri, path, properties.getCredentialForUser());
    }

    @ShellMethod(value = "Переключить ветку в полученном из gitlab проекте.", key = "gch")
    public void gitCheckOut(
            @ShellOption(value = {"-b", "--branch"}, defaultValue = "origin/test",
                    help = "На какую ветку переключится в локальном проекте(по умолчанию test") String branch,
            @ShellOption(value = {"-p", "--path"}, defaultValue = "c:/temp/",
                    help = "Путь в каталог куда клонирован проект.") File path) throws IOException, GitAPIException {
        gitCommandService.gitCheckOut(branch, path, properties.getCredentialForUser());
    }

    @ShellMethod(value = "Сохранить и передать изменения во внешнее хранилище(gitlab)", key = "gcp")
    public void gitCommitAndPush(
            @ShellOption(value = {"-u", "--uri"}, defaultValue = "https://gitlab.skblab.ru/camunda_processes/fssp.git",
                    help = "Ссылка на проект в gitlab.") String uri,
            @ShellOption(value = {"-p", "--path"}, defaultValue = "c:/temp/",
                    help = "Путь в каталог ОТКУДА пушить проект.") File path) throws GitAPIException, IOException {
        gitCommandService.gitCommitAndPush(uri, path, "", properties.getUsername(),
                "", properties.getCredentialForUser());
    }
}
