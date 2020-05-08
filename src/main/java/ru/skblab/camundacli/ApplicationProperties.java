package ru.skblab.camundacli;

import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "camundacli")
public class ApplicationProperties {

    private String url;
    private String username;
    private String password;
    private String activeProject;
    private String projectsDir;
    private String email;
    private String fileIdDeployment;
    private String camundaRestEngineUrl;

    private Map<String, String> projects;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getProjects() {
        return projects;
    }

    public void setProjects(Map<String, String> projects) {
        this.projects = projects;
    }

    public UsernamePasswordCredentialsProvider getCredentialForUser() {
        return new UsernamePasswordCredentialsProvider(this.username, this.password);
    }

    public String getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(String activeProject) {
        this.activeProject = activeProject;
    }

    public String getProjectsDir() {
        return projectsDir;
    }

    public void setProjectsDir(String projectsDir) {
        this.projectsDir = projectsDir;
    }

    public String getUriGitActiveProject() {
        return getUrl() + activeProject + ".git";
    }

    public File getPathActiveProject() {
        return new File(getProjectsDir() + activeProject);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFileIdDeployment() {
        return fileIdDeployment;
    }

    public void setFileIdDeployment(String fileIdDeployment) {
        this.fileIdDeployment = fileIdDeployment;
    }

    public String getCamundaRestEngineUrl() {
        return camundaRestEngineUrl;
    }

    public void setCamundaRestEngineUrl(String camundaRestEngineUrl) {
        this.camundaRestEngineUrl = camundaRestEngineUrl;
    }
}


