package ru.skblab.camundacli.camunda;

import java.io.File;
import java.io.IOException;

public interface CamundaDeployer {
    void deployDiagram(String extension, File path, String deploymentName) throws IOException;
}
