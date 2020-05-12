package ru.skblab.camundacli.shell;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.maven.shared.invoker.*;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.skblab.camundacli.core.ShellHelper;

import java.io.File;
import java.util.Collections;

@ShellComponent
@Slf4j
@AllArgsConstructor
public class MavenExternalCommand {

    private final ShellHelper shellHelper;

    @ShellMethod(value = "Проверка контекста среды(maven, java)", key = "ch")
    public String checkContext() {
        if (runMavenCommand("--version")) {
            return getAnsiString("Контекст готов, проверка успешна.", AnsiColor.BRIGHT_MAGENTA);
        } else {
            return getAnsiString("Контекст не готов, проверка не успешна.", AnsiColor.BRIGHT_RED);
        }
    }

    @ShellMethod(value = "Деплой собранного маршрута", key = "d")
    public String deploy() {
        return "";
    }

    @ShellMethod(value = "Собрать maven проект", key = "b")
    public String build() {
        if (runMavenCommand("install -Dmaven.test.skip=true")) {
            return getAnsiString("Сборка проведена успешно.", AnsiColor.BRIGHT_MAGENTA);
        } else {
            return getAnsiString("Сборку провести не удалось!", AnsiColor.BRIGHT_RED);
        }
    }

    @ShellMethod(value = "Собрать и если успешно - задеплоить", key = "bd")
    public String buildAndDeploy() {
        return "";
    }

    private String getAnsiString(String outString, AnsiColor color) {
        if (color == null) {
            color = AnsiColor.BRIGHT_MAGENTA;
        }

        return AnsiOutput.toString(color, outString);
    }

    private Boolean runMavenCommand(String command) {
        Invoker invoker = new DefaultInvoker();
        String mavenHome = System.getenv().get("MAVEN_HOME");
        if (mavenHome == null) {
            shellHelper.printError("Не установлена переменная среды MAVEN_HOME");
            return false;
        }

        invoker.setMavenHome(new File(mavenHome));
        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals(Collections.singletonList(command));

        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            shellHelper.printError("Ошибка при выполнении команды " + command);
            log.error("{}", e.getMessage());
            log.error("{}", ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }
}
