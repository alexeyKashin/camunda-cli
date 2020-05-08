package ru.skblab.camundacli.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.skblab.camundacli.services.MavenEmbeddedService;

@ShellComponent
@AllArgsConstructor
public class MavenEmbeddedCommandShell {
    private final MavenEmbeddedService mavenEmbeddedService;

    @ShellMethod(value = "Встроенный maven, версия", key = "v")
    public void executeMavenInstall() {
        mavenEmbeddedService.mavenVersion();
    }
}
