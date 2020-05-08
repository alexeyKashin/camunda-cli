package ru.skblab.camundacli.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skblab.camundacli.core.MavenEmbeddedClient;

@Service
@AllArgsConstructor
public class MavenEmbeddedServiceImpl implements MavenEmbeddedService {

    private final MavenEmbeddedClient mavenEmbeddedClient;

    @Override
    public int mavenVersion() {
        return mavenEmbeddedClient.mavenVersion();
    }
}
