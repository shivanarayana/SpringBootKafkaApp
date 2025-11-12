package com.howtodoinjava.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicHealthCheckController {

    @Autowired
    private HealthCheckExecutorService healthCheckExecutorService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * New Endpoint: http://localhost:9001/actuator/dynamichealthcheck
     */
    @GetMapping("/actuator/dynamichealthcheck")
    public ResponseEntity<String> dynamicHealthCheck(
            @RequestHeader(value = "X-Caller-Health-Url") String callerUrl) {

        String serviceName = callerUrl.contains("localhost") ? "test-manager-dynamic" : "dynamic-service";

        // 1. Create a temporary ServiceConfig object for the check
        ExternalServiceProperties.ServiceConfig dynamicConfig = new ExternalServiceProperties.ServiceConfig();
        dynamicConfig.setUrl(callerUrl);
        dynamicConfig.setFlow("DYNAMIC_HEALTH_CHECK");

        try {
            // 2. REUSE LOGIC: Call the shared health checking method
            Health health = healthCheckExecutorService.checkExternalService(serviceName, dynamicConfig);

            // 3. Convert the Spring Health object back to a JSON string for the response
            String jsonResponse = objectMapper.writeValueAsString(health);

            // 4. Return the result to the caller (Test Manager)
            HttpStatus status = health.getStatus().equals(org.springframework.boot.actuate.health.Status.UP)
                    ? HttpStatus.OK : HttpStatus.SERVICE_UNAVAILABLE;

            return ResponseEntity.status(status).body(jsonResponse);

        } catch (Exception e) {
            // Handle catastrophic failures (e.g., JSON conversion error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"status\": \"DOWN\", \"service_checked\":\"" + serviceName + "\", \"reason\":\"Internal error during health check processing.\" }");
        }
    }
}