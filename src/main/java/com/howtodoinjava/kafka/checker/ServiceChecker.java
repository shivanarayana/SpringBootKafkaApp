package com.howtodoinjava.kafka.checker;

import org.springframework.boot.actuate.health.CompositeHealth; // New Import!
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ServiceChecker {

    private final HealthEndpoint healthEndpoint;

    // Spring Boot automatically provides the HealthEndpoint bean
    public ServiceChecker(HealthEndpoint healthEndpoint) {
        this.healthEndpoint = healthEndpoint;
    }

    public boolean isInfrastructureReady() {
        // 1. healthEndpoint.health() returns HealthComponent. We cast it to CompositeHealth
        // to access the getComponents() method, resolving the compiler error.
        HealthComponent rootHealth = healthEndpoint.health();

        if (!(rootHealth instanceof CompositeHealth)) {
            System.err.println("Checker Error: Health endpoint did not return CompositeHealth. Cannot check individual components.");
            return rootHealth.getStatus().equals(Status.UP);
        }

        CompositeHealth compositeHealth = (CompositeHealth) rootHealth;

        // 2. Now we can safely call getComponents()
        Map<String, HealthComponent> components = compositeHealth.getComponents();

        // 3. Check Kafka and Camunda statuses
        HealthComponent kafkaHealth = components.get("kafka");
        HealthComponent camundaHealth = components.get("camunda");

        boolean isKafkaUp = kafkaHealth != null && kafkaHealth.getStatus().equals(Status.UP);
        boolean isCamundaUp = camundaHealth != null && camundaHealth.getStatus().equals(Status.UP);

        System.out.println("Checker Status: Kafka UP=" + isKafkaUp + ", Camunda UP=" + isCamundaUp);

        return isKafkaUp && isCamundaUp;
    }
}