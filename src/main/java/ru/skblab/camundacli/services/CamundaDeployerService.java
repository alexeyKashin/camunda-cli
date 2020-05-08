package ru.skblab.camundacli.services;

import java.io.File;
import java.io.IOException;

public interface CamundaDeployerService {
    void deployProject(String extension, File path, String deploymentName) throws IOException;
    void deployProjectPackage(String project) throws IOException;
}
