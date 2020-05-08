package ru.skblab.camundacli.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skblab.camundacli.ApplicationProperties;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

@Service
@AllArgsConstructor
@Slf4j
public class FileSystemManagerImpl implements FileSystemManager {

    private final ApplicationProperties applicationProperties;
    private final ShellHelper shellHelper;

    @Override
    public String getApplicationPath() {
        return System.getProperty("user.dir");
    }

    @Override
    public List<File> getDiagrams(File rootDir, String extension) {
        List<File> result = new ArrayList<>();

        LinkedList<File> dirList = new LinkedList<>();
        if (rootDir.isDirectory()) {
            dirList.addLast(rootDir);
        }

        while (dirList.size() > 0) {
            File[] filesList = dirList.getFirst().listFiles();
            if (filesList != null) {
                for (File path : filesList) {
                    if (path.isDirectory()) {
                        dirList.addLast(path);
                    } else {
                        String simpleFileName = path.getName();

                        if (simpleFileName.endsWith(extension)) {
                            result.add(path);
                        }
                    }
                }
            }
            dirList.removeFirst();
        }
        return result;
    }

    /**
     * Проверяем, есть ли каталог проекта
     *
     * @param project - наименование проекта из списка проектов
     * @return true - директория есть
     * false - директории нет
     */
    @Override
    public Boolean checkBaseProjectDir(String project) {
        return Files.isDirectory(getPathNameProject(project));
    }

    /**
     * Удаление ппки проекта со всеи содержимым.
     *
     * @param project
     * @return true - удалено успешно
     * false - удалить не удалось
     */
    @Override
    public Boolean deleteProjectDir(String project) throws URISyntaxException {
        return deleteFilesAndFolder(getPathNameProject(project).toFile());
    }

    /**
     * Читаем из файла deploy_id.txt идентификатор для деплоя
     *
     * @param project
     * @return
     */
    @Override
    public String getDefaultDeploymentId(String project) {
        String pathDefaultIdProject = applicationProperties.getPathActiveProject().toString() + "\\" + applicationProperties.getFileIdDeployment();
        log.info("Читаем файл " + pathDefaultIdProject);
        try {
            Scanner in = new Scanner(new File(pathDefaultIdProject));
            String id = in.nextLine();
            if (id.matches("^[a-zA-Z0-9]+")) {
                shellHelper.printSuccess("DeploymentName успешно прочитан - " + id);
                return id;
            } else {
                return "defaultDeploymentName";
            }
        } catch (Exception e) {
            return "defaultDeploymentName";
        }
    }

    private Boolean deleteFilesAndFolder(File file) {
        try {
            if (!file.exists()) {
                return true;
            }
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    deleteFilesAndFolder(f);
                }
            }
            file.delete();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Path getPathNameProject(String nameProject) {
        return Path.of(applicationProperties.getProjectsDir() + nameProject);
    }
}
