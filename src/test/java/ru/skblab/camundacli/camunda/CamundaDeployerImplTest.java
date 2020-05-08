package ru.skblab.camundacli.camunda;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skblab.camundacli.core.FileSystemManager;
import ru.skblab.camundacli.core.ShellHelper;

import java.io.File;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class CamundaDeployerImplTest {
    @Mock
    private CamundaRestClient restClient;
    @Mock
    private FileSystemManager fileSystemManager;
    @Mock
    private ShellHelper shellHelper;

    private CamundaDeployer camundaDeployer;
    @BeforeEach
    public void init() {
        camundaDeployer = new CamundaDeployerImpl(restClient, fileSystemManager, shellHelper);
    }

    @Test
    public void deployDiagram_whenEmptyList() throws Exception {
        when(fileSystemManager.getDiagrams(any(), any())).thenReturn(Collections.emptyList());
        camundaDeployer.deployDiagram(".bpmn", new File("12345"), "54321");

        verify(fileSystemManager, times(1)).getDiagrams(any(), any());
        verify(shellHelper, times(1)).printError(any());
        verify(restClient, times(0)).deployDiagram(any(), anyList());
    }

    @Test
    public void deployDiagram_whenNotEmptyList() throws Exception {
        when(fileSystemManager.getDiagrams(any(), any())).thenReturn(Collections.singletonList(new File("54321")));
        camundaDeployer.deployDiagram(".bpmn", new File("12345"), "54321");

        verify(fileSystemManager, times(1)).getDiagrams(any(), any());
        verify(shellHelper, times(0)).printError(any());
        verify(restClient, times(1)).deployDiagram(any(), anyList());
    }
}