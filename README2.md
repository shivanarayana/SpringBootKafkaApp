
**ARCHITECTURE REPORT:** 

Health Check Mechanism
This report details the classes responsible for generating the raw service status output in Spring Boot Actuator, 
up to the point of consolidation.

Section I: Kafka Health Check (The Auto-Configured UP Status)
The Kafka health check is largely automated by Spring Boot Actuator's integration with the Spring for Kafka library.

application.yaml : This file provides the necessary connection details for the Kafka health check to function:
Role: Defines the target server.

Spring Boot Auto-Configuration (The Enabler)
Role: Spring Boot contains internal classes (like KafkaHealthIndicatorAutoConfiguration) that automatically detect if Kafka is on the classpath and if Actuator is enabled.

KafkaHealthIndicator (The Actual Checker)
Role: This is the internal Spring class that implements the HealthIndicator interface for Kafka.

Mechanism: It creates and uses a Kafka AdminClient (a part of the standard Kafka client library) to connect to the configured bootstrap-servers 
and retrieve cluster metadata (like the clusterId and nodeCount).



Spring Actuator Framework (The Collector)
Role: The core Actuator infrastructure scans the application context for all beans that implement the HealthIndicator interface (both the auto-configured Kafka one and your custom Camunda one).

Output Object: It aggregates the results from all indicators into a single object of type CompositeHealth.



What is Camunda?
Camunda allows organizations to design, automate,
and monitor complex business processes that span multiple systems, services, and human tasks4

Camunda is an external, non-Spring-managed service, its health check required a custom implementation.

1. CamundaHealthIndicator.java (The Custom Checker)
   Role: This custom class, created based on your requirements, is the manual implementation of the HealthIndicator interface.

Dependencies: It relies on Spring's @Value annotation to read the Camunda URL from application.yaml (${test-harness.camunda.base-url}).

Mechanism:

It implements the health() method.

Inside health(), it uses a networking utility (like Spring's RestTemplate or WebClient) to perform an HTTP GET request to a simple Camunda endpoint (e.g., /engine-rest/version).


CUCUMBER BDDS:
TestManager & ServiceChecker: Wrote the Java classes to consume the raw health data and apply the orchestration logic

TestLoaderRunner.java to launch the BDD engine.

Created CucumberSpringConfiguration.java and fine-tuned the GLUE_PROPERTY_NAME in the runner to successfully connect the Cucumber test execution environment to your running Spring application context.

Created infrastructure.feature and SetupSteps.java to validate that the final status retrieved by the ServiceChecker is exactly what was expected.

