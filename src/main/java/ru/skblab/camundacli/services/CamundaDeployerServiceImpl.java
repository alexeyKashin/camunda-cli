package ru.skblab.camundacli.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skblab.camundacli.camunda.CamundaDeployer;
import ru.skblab.camundacli.core.FileSystemManager;
import ru.skblab.camundacli.ApplicationProperties;

import java.io.File;
import java.io.IOException;

@Service
@AllArgsConstructor
public class CamundaDeployerServiceImpl implements CamundaDeployerService {

    private final CamundaDeployer camundaDeployer;
    private final ApplicationProperties applicationProperties;
    private final FileSystemManager fileSystemManager;

    @Override
    public void deployProject(String extension, File path, String deploymentName) throws IOException {
        camundaDeployer.deployDiagram(extension, path, deploymentName);
    }

    @Override
    public void deployProjectPackage(String project) throws IOException {
        String activeProject = applicationProperties.getActiveProject();
        if (!activeProject.equals(project)) {
            applicationProperties.setActiveProject(project);
        }
        camundaDeployer.deployDiagram(".bpmn", applicationProperties.getPathActiveProject(), fileSystemManager.getDefaultDeploymentId(project));
        applicationProperties.setActiveProject(activeProject);
    }
}
