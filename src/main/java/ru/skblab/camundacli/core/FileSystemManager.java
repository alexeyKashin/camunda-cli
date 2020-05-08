package ru.skblab.camundacli.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

public interface FileSystemManager {

    String getApplicationPath();
    List<File> getDiagrams(File rootDir, String extension);
    Boolean checkBaseProjectDir(String project) throws URISyntaxException;
    Boolean deleteProjectDir(String project) throws URISyntaxException;
    String getDefaultDeploymentId(String project) throws FileNotFoundException;

}
