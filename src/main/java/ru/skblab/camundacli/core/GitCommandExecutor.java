package ru.skblab.camundacli.core;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public interface GitCommandExecutor {
    void gitClone(String uri, File path, UsernamePasswordCredentialsProvider credentialsProvider) throws GitAPIException;

    void gitCommitAndPush(String uri, File path, String commitMessage, String userName, String email, UsernamePasswordCredentialsProvider credentialsProvider) throws IOException, GitAPIException;

    void gitCheckOut(String branche, File path, UsernamePasswordCredentialsProvider credentialsProvide) throws IOException, GitAPIException;

}
