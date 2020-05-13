package ru.skblab.camundacli.camunda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.skblab.camundacli.ApplicationProperties;
import ru.skblab.camundacli.core.ShellHelper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CamundaRestClientImpl implements CamundaRestClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String engineRestUrl;
    private final ShellHelper shellHelper;
    @Autowired
    public CamundaRestClientImpl(ShellHelper shellHelper, ApplicationProperties applicationProperties) {
        this.shellHelper = shellHelper;
        this.engineRestUrl = applicationProperties.getCamundaRestEngineUrl();
    }

    @Override
    public String healthCheck() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Content-type", "application/json; charset=utf-8");
        ResponseEntity<String> exchange = restTemplate.getForEntity(engineRestUrl + "version", String.class, headers);
        HttpStatus status = exchange.getStatusCode();
        if (!status.equals(HttpStatus.OK)) {
            String errorMsg = String.format("Не успешный запрос в сервис %s %s",
                    status.toString(), status.getReasonPhrase());
            shellHelper.printError(errorMsg);
            shellHelper.printError(String.format("Статус ответа %s Тело ответа %s", status, exchange.getBody()));
        }
        return exchange.getBody();
    }

    @Override
    public void deployDiagram(String deploymentName, List<File> diagrams) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> requestBody = createRequestBody(deploymentName, diagrams);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = restTemplate
                .postForEntity(engineRestUrl + "deployment/create", requestEntity, String.class);
        HttpStatus status = response.getStatusCode();
        if (!status.equals(HttpStatus.OK)) {
            shellHelper.printError("Произошла ошибка деплоя маршрута " + deploymentName);
            shellHelper.printError(response.getBody());
            return;
        }
        shellHelper.printSuccess("Маршрут " + deploymentName + " заргужен успешно.");
    }

    private MultiValueMap<String, Object> createRequestBody(String deploymentName, List<File> diagrams) {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("deployment-name", deploymentName);
        requestBody.add("enable-duplicate-filtering", "true");
        requestBody.add("deployment-source", "process application");

        diagrams.forEach(file -> {
            requestBody.add("data", file);
        });
        return requestBody;
    }
}
