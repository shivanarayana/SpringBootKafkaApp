package com.howtodoinjava.kafka;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class acts as the Service Manager, dynamically creating and registering
 * a HealthIndicator for every external service defined in application.yaml.
 * It replaces the static CamundaHealthIndicator.java.
 *
 * Reads ExternalServiceProperties.getServices() map. *
 * For each map entry (name, config), it creates a HealthIndicator lambda that invokes checkExternalService(name, config). *
 * Registers the indicator under the name key into the HealthContributorRegistry or via Spring Boot Actuator registration API.
 */
@Component
public class DynamicHealthCheckRegistrar implements InitializingBean {

    private final HealthContributorRegistry registry;
    private final ExternalServiceProperties properties;
    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    public DynamicHealthCheckRegistrar(
            HealthContributorRegistry registry,
            ExternalServiceProperties properties) {
        this.registry = registry;
        this.properties = properties;
    }

    @Override
    public void afterPropertiesSet() {
        // Register a health indicator for each external service
        properties.getServices().forEach((name, config) -> {
            HealthIndicator indicator = () -> checkExternalService(name, config);
            registry.registerContributor(name, indicator);
        });
    }

    private Health checkExternalService(String name, ExternalServiceProperties.ServiceConfig config) {
        String url = config.getUrl();
        String flow = config.getFlow();

        try { // Performs an HTTP GET to config.getUrl() using RestTemplate
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            Health.Builder builder = response.getStatusCode().is2xxSuccessful() ? Health.up() : Health.down();
            builder.withDetail("url", url);

            if (flow != null) {
                builder.withDetail("flow", flow);
            }

            builder.withDetail("message",
                    response.getStatusCode().is2xxSuccessful()
                            ? "Connection successful"
                            : "Unexpected status: " + response.getStatusCode());

            return builder.build();

        } catch (Exception e) {
            Health.Builder builder = Health.down(e)
                    .withDetail("url", url)
                    .withDetail("message", "Failed to connect to " + name);
            if (flow != null) {
                builder.withDetail("flow", flow);
            }
            return builder.build();
        }
    }

}
