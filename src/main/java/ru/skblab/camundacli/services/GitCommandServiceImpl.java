package ru.skblab.camundacli.services;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import ru.skblab.camundacli.core.GitCommandExecutor;
import ru.skblab.camundacli.ApplicationProperties;

import java.io.File;
import java.io.IOException;

@Service
public class GitCommandServiceImpl implements GitCommandService {

    private final GitCommandExecutor gitCommandExecutor;
    private final ApplicationProperties applicationProperties;

    public GitCommandServiceImpl(GitCommandExecutor gitCommandExecutor, ApplicationProperties applicationProperties) {
        this.gitCommandExecutor = gitCommandExecutor;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void gitClone(String uri, File path, UsernamePasswordCredentialsProvider credentialsProvide) throws GitAPIException {
        gitCommandExecutor.gitClone(uri, path, credentialsProvide);
    }

    @Override
    public void gitCheckOut(String branch, File path, UsernamePasswordCredentialsProvider credentialsProvide) throws IOException, GitAPIException {
        gitCommandExecutor.gitCheckOut(branch, path, credentialsProvide);
    }

    @Override
    public void gitCommitAndPush(String uri, File path, String commitMessage, String userName, String email, UsernamePasswordCredentialsProvider credentialsProvider) throws IOException, GitAPIException {
        gitCommandExecutor.gitCommitAndPush(uri, path, commitMessage, userName, email, credentialsProvider);
    }

    @Override
    public void gitCommitAndPushProject(String project, String commitMessage) throws IOException, GitAPIException {
        gitCommitAndPush(applicationProperties.getUriGitActiveProject(), applicationProperties.getPathActiveProject(), commitMessage, applicationProperties.getUsername(), applicationProperties.getEmail(), applicationProperties.getCredentialForUser());
    }

    @Override
    public void gitCloneProject(String project) throws GitAPIException {
        gitClone(applicationProperties.getUriGitActiveProject(), applicationProperties.getPathActiveProject(), applicationProperties.getCredentialForUser());
    }
}
