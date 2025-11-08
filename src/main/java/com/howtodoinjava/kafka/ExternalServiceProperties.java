package com.howtodoinjava.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Maps the 'external-services-to-check' section from application.yaml.
 * This makes the list of services dynamic and easy to configure.
 */@ConfigurationProperties(prefix = "external")
public class ExternalServiceProperties {
// Holds a Map<String, ServiceConfig> where each entry key becomes the health indicator key (e.g., camunda-workflow-engine) and ServiceConfig has fields such as url and flow.
    private Map<String, ServiceConfig> services = new HashMap<>();

    public Map<String, ServiceConfig> getServices() {
        return services;
    }

    public void setServices(Map<String, ServiceConfig> services) {
        this.services = services;
    }

    public static class ServiceConfig {
        private String url;
        private String flow;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFlow() {
            return flow;
        }

        public void setFlow(String flow) {
            this.flow = flow;
        }
    }
}
