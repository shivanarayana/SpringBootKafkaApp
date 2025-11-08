Health check flow

External system calls GET /actuator/health (or Reply/Test Manager polls this endpoint).

Spring Boot Actuator collects contributions from:

Built-in indicators (diskSpace, ping, etc.).

KafkaClusterHealthIndicator (registered as bean named kafka). It uses AdminClient.describeCluster() to fetch clusterId and nodes(); returns UP if successful.

DynamicHealthCheckRegistrar contributions — for every service defined in external.services in configuration it registers a HealthIndicator.

The indicator performs an HTTP GET (via RestTemplate) to the service's url. If status is 200 it returns UP with details; otherwise DOWN with details.

camunda-workflow-engine is ensured as a default entry (so the health response always contains this key).

Actuator aggregates these contributions into a single JSON payload returned to the caller.

5. Detailed class-by-class explanation (what each class does, how it was changed)
   SpringBootKafkaAppApplication.java

What it is: Standard Spring Boot @SpringBootApplication annotated class.

Key detail: Annotated with @EnableConfigurationProperties(ExternalServiceProperties.class) which instructs Spring to bind properties to the ExternalServiceProperties POJO.

Calls / Called by: It is the application entry point; starts Spring context.

application.yaml

What it is: Configuration file. Typical keys used in this project:

spring.kafka.bootstrap-servers (Kafka connection string)

management.endpoints.web.exposure.include and management.endpoint.health.show-details to expose health details

external.services map for dynamic service configuration (for example camunda-workflow-engine, google-homepage, internal-rest-api).

How it is used: ExternalServiceProperties reads the external.services map at startup.

AppConstants.java

What it is: Holds constant strings like TOPIC_NAME and GROUP_ID.

Why: Keeps topic and group IDs in one place so both producer & consumer use the same values.

ActuatorConfig.java

What it is: Configuration class that declares beans used by health checks.

Important beans:

AdminClient for Kafka — created with spring.kafka.bootstrap-servers value.

RestTemplate bean used by DynamicHealthCheckRegistrar for HTTP calls to external services.

Why: Centralizes creation of clients used in health indicators and ensures they are managed by Spring.

ExternalServiceProperties.java

What it is: @ConfigurationProperties(prefix = "external") class.

Structure: Holds a Map<String, ServiceConfig> where each entry key becomes the health indicator key (e.g., camunda-workflow-engine) and ServiceConfig has fields such as url and flow.

Changes made: Field getters must safely handle missing values OR DynamicHealthCheckRegistrar must avoid passing nulls to Health.Builder.withDetail() (you implemented Option 1). This prevents IllegalArgumentException.

DynamicHealthCheckRegistrar.java

What it is: The class that dynamically registers health indicators for each configured external service.

How it works (high-level):

Reads ExternalServiceProperties.getServices() map.

For each map entry (name, config), it creates a HealthIndicator lambda that invokes checkExternalService(name, config).

Registers the indicator under the name key into the HealthContributorRegistry or via Spring Boot Actuator registration API.

What checkExternalService() does:

Performs an HTTP GET to config.getUrl() using RestTemplate.

Constructs a Health response:

For successful (2xx) responses: Health.up() with url, optional flow, and message "Connection successful".

For non-2xx responses: Health.down() with url, optional flow, and message describing the status.

For exceptions: Health.down(exception) with url, optional flow, and message "Failed to connect to ".

Important fix implemented (Option 1): Only call .withDetail("flow", ...) when flow != null so withDetail never receives a null and throws.

Why this class matters: This is the glue that creates the behavior you asked for — the actuator health payload now contains dynamically configured keys (e.g., camunda-workflow-engine, google-homepage).

KafkaClusterHealthIndicator.java

What it is: A custom HealthIndicator bean that checks Kafka cluster connectivity.

How it works: Uses AdminClient.describeCluster() calls with a short timeout to fetch clusterId and nodes() count.

Outputs: On success: Health.up() with clusterId, nodeCount, status string. On failure or timeout: Health.down() with explanatory details.

Why: Actuator doesn’t include a Kafka broker connectivity indicator out-of-the-box; this supplies a robust check.

KafKaProducerService.java and KafkaProducerController.java

What they are: Simple producer service & controller to publish messages to Kafka for testing.

Flow: Controller receives request → calls producer → producer uses KafkaTemplate to send.

Why: Helps testing the Kafka integration and demonstrates producer usage.

KafKaConsumerService.java

What it is: A simple consumer annotated with @KafkaListener that logs received messages.

Why: Demonstrates consumption and verifies messages published by the producer reach Kafka and are processed.