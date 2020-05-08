package ru.skblab.camundacli.services;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;

public interface GitCommandService {
    void gitClone(String uri, File path, UsernamePasswordCredentialsProvider credentialsProvide) throws GitAPIException;
    void gitCheckOut(String branch, File path, UsernamePasswordCredentialsProvider credentialsProvide) throws IOException, GitAPIException;
    void gitCommitAndPush(String uri, File path, String commitMessage, String userName, String email, UsernamePasswordCredentialsProvider credentialsProvider) throws IOException, GitAPIException;
    void gitCommitAndPushProject(String project, String commitMessage) throws IOException, GitAPIException;
    void gitCloneProject(String project) throws GitAPIException;
}
