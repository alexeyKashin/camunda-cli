package ru.skblab.camundacli;

import org.jline.utils.AttributedString;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class CliPromptProvider implements PromptProvider {

    private ApplicationProperties applicationProperties;

    public CliPromptProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @Override
    public AttributedString getPrompt() {
        String basePrompt = "camunda-cli(%s):>";
        String prompt = (applicationProperties.getActiveProject() == null || applicationProperties.getActiveProject().isEmpty()) ? String.format(basePrompt, "проект не выбран") : String.format(basePrompt, applicationProperties.getActiveProject());
        return new AttributedString(AnsiOutput.toString(AnsiColor.CYAN, prompt));
    }

}
