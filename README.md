**Declarative Orchestration**

The docker-compose.yaml as the Blueprint 
The YAML file created serves as the single source of truth (the blueprint) for your infrastructure stack:

Role in Kafka Setup services 
Defines all individual components in the stack.Defines both the zookeeper and kafka services.

In Command prompt go to the project location
The Command: docker compose up -dCommand Part
Function 
docker compose 
Tells the Docker engine to use the multi-container orchestration tool.up 
Instructs Compose to create and start all services defined in the YAML file.-d (Detached) 
Runs the services in the background, so you get control of your terminal back immediately.

Run the application:
./mvnw spring-boot:run

Now Up the Spring application 
HIT the API:
http://localhost:9000/publish?message=FinalKafkaAPICall%20is%20working to send messages
Hit the API:
http://localhost:9000/actuator/health

Publish flow

Client calls GET /publish?message=Hello on KafkaProducerController.

Controller calls KafKaProducerService.sendMessage("Hello").

KafKaProducerService uses KafkaTemplate<String, String> to send the message to the configured topic.

KafKaConsumerService (if running and in the same consumer group or different) receives the message via @KafkaListener and logs it.

Health check flow

External system calls GET /actuator/health (or Reply/Test Manager polls this endpoint).

Spring Boot Actuator collects contributions from:

Built-in indicators (diskSpace, ping, etc.).

KafkaClusterHealthIndicator (registered as bean named kafka). It uses AdminClient.describeCluster() to fetch clusterId and nodes(); returns UP if successful.

DynamicHealthCheckRegistrar contributions — for every service defined in external.services in configuration it registers a HealthIndicator.

The indicator performs an HTTP GET (via RestTemplate) to the service's url. If status is 200 it returns UP with details; otherwise DOWN with details.

camunda-workflow-engine is ensured as a default entry (so the health response always contains this key).

Actuator aggregates these contributions into a single JSON payload returned to the caller.