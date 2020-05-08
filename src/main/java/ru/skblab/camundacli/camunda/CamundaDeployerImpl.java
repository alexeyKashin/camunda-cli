package ru.skblab.camundacli.camunda;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skblab.camundacli.core.FileSystemManager;
import ru.skblab.camundacli.core.ShellHelper;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CamundaDeployerImpl implements CamundaDeployer {

    private final CamundaRestClient restClient;
    private final FileSystemManager fileSystemManager;
    private final ShellHelper shellHelper;

    @Override
    public void deployDiagram(String extension, File path, String deploymentName) throws IOException {
        List<File> diagrams = fileSystemManager.getDiagrams(path, extension);
        if (diagrams.isEmpty()) {
            shellHelper.printError("Диаграммы для деплоя не обнаружены в каталоге " + path.getAbsoluteFile());
            return;
        }
        restClient.deployDiagram(deploymentName, diagrams);
    }
}
