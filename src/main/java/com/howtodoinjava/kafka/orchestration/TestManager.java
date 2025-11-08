package com.howtodoinjava.kafka.orchestration;

import com.howtodoinjava.kafka.config.ConfigManager;
import com.howtodoinjava.kafka.checker.ServiceChecker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestManager {

    private final ConfigManager configManager;
    private final ServiceChecker serviceChecker;

    @Autowired
    public TestManager(ConfigManager configManager, ServiceChecker serviceChecker) {
        this.configManager = configManager;
        this.serviceChecker = serviceChecker;

        // This method will run immediately after the bean is created (on startup)
        initializeTestHarness();
    }

    public void initializeTestHarness() {
        System.out.println("--- TestManager: Initializing ---");

        // 1. ConfigManager – to load settings
        System.out.println("Config Loaded: Input Topic: " + configManager.getInputTopic());
        System.out.println("Config Loaded: Camunda URL: " + configManager.getCamunda().getBaseUrl());

        // 2. ServiceChecker – to validate infrastructure
        System.out.println("Running infrastructure check...");
        if (serviceChecker.isInfrastructureReady()) {
            System.out.println("Infrastructure is READY. Proceeding to TestLoader.");
        } else {
            // Since Camunda is DOWN, this will fire:
            System.err.println("Infrastructure check FAILED. Cannot proceed with tests.");
            // In a real application, you might throw an exception here to halt the test suite.
        }
    }
}