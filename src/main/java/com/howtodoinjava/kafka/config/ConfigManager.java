package com.howtodoinjava.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "test-harness")
public class ConfigManager { //(Kafka clients, Camunda clients, result reports)
    // ConfigManager reads these YAML values and maps them into strongly-typed Java fields

    private String inputTopic;
    private String outputTopic;
    private CamundaConfig camunda = new CamundaConfig();

    // Nested Configuration Class
    public static class CamundaConfig {
        private String baseUrl;
        private String processDefinitionKey;

        // Getters and Setters
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
        public String getProcessDefinitionKey() { return processDefinitionKey; }
        public void setProcessDefinitionKey(String processDefinitionKey) { this.processDefinitionKey = processDefinitionKey; }
    }

    // Getters and Setters for top-level properties
    public String getInputTopic() { return inputTopic; }
    public void setInputTopic(String inputTopic) { this.inputTopic = inputTopic; }
    public String getOutputTopic() { return outputTopic; }
    public void setOutputTopic(String outputTopic) { this.outputTopic = outputTopic; }
    public CamundaConfig getCamunda() { return camunda; }
    public void setCamunda(CamundaConfig camunda) { this.camunda = camunda; }
}