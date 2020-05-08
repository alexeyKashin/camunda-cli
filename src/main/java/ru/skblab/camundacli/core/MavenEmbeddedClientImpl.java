package ru.skblab.camundacli.core;

import org.apache.maven.cli.MavenCli;
import org.springframework.stereotype.Component;

@Component
public class MavenEmbeddedClientImpl implements MavenEmbeddedClient {

    private FileSystemManager fileSystemManager;

    public MavenEmbeddedClientImpl(FileSystemManager fileSystemManager) {
        this.fileSystemManager = fileSystemManager;
    }

    @Override
    public int mavenVersion() {
        String projectDirectory = fileSystemManager.getApplicationPath();
        MavenCli mavenCli = new MavenCli();
        System.setProperty("maven.multiModuleProjectDirectory", projectDirectory);

        return mavenCli.doMain(new String[]{"--version"}, projectDirectory, System.out, System.out);
    }
}
