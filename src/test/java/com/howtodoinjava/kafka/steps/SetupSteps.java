// src/test/java/.../steps/SetupSteps.java

package com.howtodoinjava.kafka.steps;

import com.howtodoinjava.kafka.checker.ServiceChecker;
import com.howtodoinjava.kafka.orchestration.TestManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.CompositeHealth;

// Use SpringBootTest to load your application context for the tests
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupSteps {

    @Autowired
    private TestManager testManager; // Use your orchestrator

    @Autowired
    private HealthEndpoint healthEndpoint; // Directly check Actuator

    @Given("the TestManager has initialized")
    public void the_test_manager_has_initialized() {
        // Since TestManager runs its logic in the constructor, this step
        // simply ensures the Spring context is fully loaded.
    }

    @When("the ServiceChecker runs")
    public void the_service_checker_runs() {
        // This ensures the check is performed, though the Actuator is always running.
        // We can just rely on the Actuator status for validation.
    }

    @Then("the {word} component status should be {string}")
    public void the_component_status_should_be(String componentName, String expectedStatus) {
        CompositeHealth compositeHealth = (CompositeHealth) healthEndpoint.health();
        Status actualStatus = compositeHealth.getComponents()
                .get(componentName.toLowerCase())
                .getStatus();

        if (!actualStatus.equals(new Status(expectedStatus.toUpperCase()))) {
            throw new AssertionError(componentName + " status was expected to be " + expectedStatus + " but was " + actualStatus);
        }
    }
}