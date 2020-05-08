package ru.skblab.camundacli.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.*;
import ru.skblab.camundacli.services.CamundaDeployerService;

import java.io.File;
import java.io.IOException;

@ShellComponent
@ShellCommandGroup("Команды работы с камундой напрямую.")
@AllArgsConstructor
public class CamundaDeployerShell {

    private final CamundaDeployerService camundaDeployerService;

    @ShellMethodAvailability()
    @ShellMethod(value = "Деплой всех найденных файлов текущего проекта в CAMUNDA (ищет включая подкаталоги)", key = "dc")
    public void deployProject(
            @ShellOption(value = {"-e", "--ext"}, defaultValue = ".bpmn",
                    help = "Расширение файлов диаграмм.") String extension,
            @ShellOption(value = {"-p", "--path"}, defaultValue = "c:/temp/",
                    help = "Путь в каталог КУДА клонировать проект.") File path,
            @ShellOption(value = {"-dm", "--deploymentname"}, defaultValue = "defaultdeploymentname",
                    help = "Имя пакета для деплоя.") String deploymentName) throws IOException {
        camundaDeployerService.deployProject(extension, path, deploymentName);
    }
}
