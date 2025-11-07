package com.howtodoinjava.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Component("kafka") // This is the key: registers it under the 'kafka' key in the health response
public class KafkaClusterHealthIndicator implements HealthIndicator {

    // Use a short timeout for health checks
    private static final Duration HEALTH_CHECK_TIMEOUT = Duration.ofSeconds(2);
    private final AdminClient adminClient;

    // Inject the AdminClient we defined in ActuatorConfig
    public KafkaClusterHealthIndicator(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    @Override
    public Health health() {
        try {
            // Check 1: Request cluster metadata (lightweight way to test connectivity)
            String clusterId = adminClient.describeCluster()
                    .clusterId()
                    .get(HEALTH_CHECK_TIMEOUT.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);

            // Check 2: Get the number of broker nodes
            int nodeCount = adminClient.describeCluster()
                    .nodes()
                    .get(HEALTH_CHECK_TIMEOUT.toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS)
                    .size();

            // If both calls succeed without exception, Kafka is UP
            return Health.up()
                    .withDetail("clusterId", clusterId)
                    .withDetail("nodeCount", nodeCount)
                    .withDetail("status", "Broker connection successful")
                    .build();

        } catch (InterruptedException | ExecutionException e) {
            // Catch errors from the Kafka client (e.g., broker down, connection refused)
            return Health.down(e)
                    .withDetail("reason", "Failed to connect to Kafka broker or fetch cluster info.")
                    .build();
        } catch (TimeoutException e) {
            // Catch if the Kafka client takes too long to respond
            return Health.down(e)
                    .withDetail("reason", "Kafka connection timed out.")
                    .build();
        }
    }
}