package ru.skblab.camundacli.camunda;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface CamundaRestClient {
    String healthCheck();
    void deployDiagram(String deploymentName, List<File> diagrams) throws IOException;
}
