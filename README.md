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