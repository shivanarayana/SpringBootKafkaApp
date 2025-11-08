package com.howtodoinjava.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("camunda")
public class CamundaHealthIndicator implements HealthIndicator {

    // Inject the Camunda URL from application.yaml
    @Value("${test-harness.camunda.base-url:http://localhost:8080/engine-rest}")
    private String camundaUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Health health() {
        try {
            // Check a standard, lightweight Camunda endpoint
            String healthUrl = camundaUrl + "/version";
            restTemplate.getForObject(healthUrl, String.class);

            return Health.up()
                    .withDetail("url", healthUrl)
                    .build();

        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("url", camundaUrl)
                    .withDetail("message", "Failed to connect to Camunda engine.")
                    .build();
        }
    }
}