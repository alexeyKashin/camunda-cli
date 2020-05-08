package ru.skblab.camundacli;

import org.jline.terminal.Terminal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import ru.skblab.camundacli.core.ShellHelper;
import ru.skblab.camundacli.core.ShellHelperImpl;

@SpringBootApplication
@Configuration
public class CamundaCliApplication {

    public static void main(String[] args) {
        SpringApplication.run(CamundaCliApplication.class, args);
    }

    @Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal) {
        return new ShellHelperImpl(terminal);
    }
}
