package ru.skblab.camundacli.shell;

import org.apache.maven.shared.invoker.*;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.io.File;
import java.util.Collections;

@ShellComponent
public class MavenExternalCommand {

    @ShellMethod(value = "Проверка контекста среды(maven, java)", key = "ch")
    public String checkContext() {
        if (runMavenCommand("--version")) {
            return outString("Контекст готов, проверка успешна.", AnsiColor.BRIGHT_MAGENTA);
        } else {
            return outString("Контекст не готов, проверка не успешна.", AnsiColor.BRIGHT_RED);
        }
    }

    @ShellMethod(value = "Деплой собранного маршрута", key = "d")
    public String deploy() {
        return "";
    }

    @ShellMethod(value = "Собрать maven проект", key = "b")
    public String build() {
        if (runMavenCommand("install -Dmaven.test.skip=true")) {
            return outString("Сборка проведена успешно.", AnsiColor.BRIGHT_MAGENTA);
        } else {
            return outString("Сборку провести не удалось!", AnsiColor.BRIGHT_RED);
        }
    }

    @ShellMethod(value = "Собрать и если успешно - задеплоить", key = "bd")
    public String buildAndDeploy() {
        return "";
    }

    private String outString(String outString, AnsiColor color) {
        if (color == null) {
            color = AnsiColor.BRIGHT_MAGENTA;
        }

        return AnsiOutput.toString(color, outString);
    }

    private Boolean runMavenCommand(String command) {
        Invoker invoker = new DefaultInvoker();
        String mavenHome = System.getenv().get("MAVEN_HOME");
        invoker.setMavenHome(new File(mavenHome));

        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals(Collections.singletonList(command));

        try {
            invoker.execute(request);
            return true;
        } catch (MavenInvocationException e) {
            e.printStackTrace();
            return false;
        }

    }

}
