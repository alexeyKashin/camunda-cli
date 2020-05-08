package ru.skblab.camundacli.core;

public interface ShellHelper {

    void printSuccess(String message);
    void printInfo(String message);
    void printError(String message);
    String ask(String prompt);
}
