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
    private final HealthCheckExecutorService healthCheckExecutorService; // <--- NEW INJECTION

    @Autowired
    public DynamicHealthCheckRegistrar(
            HealthContributorRegistry registry,
            ExternalServiceProperties properties,
            HealthCheckExecutorService healthCheckExecutorService) { // <--- Inject the new service
        this.registry = registry;
        this.properties = properties;
        this.healthCheckExecutorService = healthCheckExecutorService; // <--- Store it
    }

    @Override
    public void afterPropertiesSet() {
        // Register a health indicator for each external service
        properties.getServices().forEach((name, config) -> {
            HealthIndicator indicator = () -> healthCheckExecutorService.checkExternalService(name, config); // <--- Reuse the public method
            registry.registerContributor(name, indicator);
        });
    }

    // *** REMOVE the private checkExternalService method from this class ***
}