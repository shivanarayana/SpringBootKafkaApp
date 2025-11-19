package com.howtodoinjava.kafka;

import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service // Make it a Spring bean for injection
public class HealthCheckExecutorService {

    private final RestTemplate restTemplate;

    public HealthCheckExecutorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String checkUrlHealth(String url) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return "UP";
            } else {
                return "DOWN";
            }
        } catch (Exception e) {
            return "DOWN";
        }
    }

    /**
     * Reusable logic to perform an HTTP health check and return a Spring Health object.
     * This method replaces the private checkExternalService() from DynamicHealthCheckRegistrar.
     */
    public Health checkExternalService(String name, ExternalServiceProperties.ServiceConfig config) {
        String url = config.getUrl();
        String flow = config.getFlow();

        try {
            // Perform the HTTP GET request
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Determine UP or DOWN based on HTTP status code
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
            // Handle connection failures and exceptions
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
