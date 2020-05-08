package ru.skblab.camundacli.core;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

@Component
@Slf4j
public class GitCommandExecutorImpl implements GitCommandExecutor {

    @Override
    public void gitClone(String uri, File path, UsernamePasswordCredentialsProvider credentialsProvider) throws GitAPIException {
        log.info("Путь к проекту - " + path.getAbsolutePath());
        Git git = Git.cloneRepository()
                .setURI(uri)
                .setDirectory(path)
                .setCredentialsProvider(credentialsProvider)
                .setBranch("test")
                .setProgressMonitor(new TextProgressMonitor())
                .call();
        git.close();
    }

    @Override
    public void gitCommitAndPush(String uri, File path, String commitMessage, String userName, String email, UsernamePasswordCredentialsProvider credentialsProvider) throws IOException, GitAPIException {
        try (Git git = Git.open(path)) {
            RevCommit commit = commitChanges(git, path, commitMessage);
            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish(uri));
            remoteAddCommand.call();

            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(credentialsProvider);
            pushCommand.call();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private RevCommit commitChanges(Git git, File path, String commitMessage) throws GitAPIException {
        git.add().addFilepattern(".").call();
        if (commitMessage.isEmpty()) {
            commitMessage = "Не заполненный message commit!";
        }
        return git.commit().setAll(true).setMessage(commitMessage).call();
    }

    @Override
    public void gitCheckOut(String branch, File path, UsernamePasswordCredentialsProvider credentialsProvide) throws IOException, GitAPIException {
        Git git = (Git) Git.open(path)
                .checkout().setName(branch).setProgressMonitor(new TextProgressMonitor()).call();
        git.close();
    }
}
