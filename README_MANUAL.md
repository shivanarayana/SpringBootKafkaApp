# SpringBootKafkaApp
SpringBootKafkaAppApplication

C:\kafka\kafka_2.12-0.10.2.1\bin\windows\>

Start ZooKeeper and Kafka BrokerYou must start ZooKeeper first, as Kafka depends on it.StepCommand 
(Navigate to C:\kafka\kafka_2.12-2.8.1 or your Kafka installation directory)
**TRADITIONAL WAY**
**1. Start ZooKeeper**
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties 
Use the correct path based on your Kafka installation directory.

**2. Start Kafka Broker**
.\bin\windows\kafka-server-start.bat .\config\server.properties 
Run this in a new terminal window after ZooKeeper is fully started.

Create the Kafka TopicYour Spring Boot application uses the topic name "test" (from AppConstants.TOPIC_NAME). 
You must create this topic manually if auto-creation is disabled in your server.properties.
Topic CreationCommand (From within the bin\windows folder) 

**3. Create Topic**
kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3 

This is the modern command using --bootstrap-server. The topic is named test with 3 partitions, as indicated in your script.

Verify the TopicVerificationCommand (From within the bin\windows folder) 

**4. List Topics**
kafka-topics.bat --list --bootstrap-server localhost:9092 

You should see test in the list.

**5. Run the Spring Boot Application**
Start Appjava -jar your-app-name.jar or run from IntelliJ

Your application should now start successfully on port 9000 as configured in application.yaml.
Test with Producer and Consumer

**6. Start Console Consumer**
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning

Run this in a new terminal to watch for messages.

**7. Hit this API**
http://localhost:9000/publish?message=HelloKafkafromSpring

This sends a message via your KafkaProducerController and KafKaProducerService.
You should see the message logged in your Spring Boot console (from the producer) and appear in the Kafka Console Consumer terminal.

From <https://www.youtube.com/watch?v=lFox22RJE7s&t=225s&ab_channel=EngineeringDigest> 

.\bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic shiva-topic

From <https://www.javainuse.com/misc/apache-kafka-hello-world> 

Verify startup:
kafka-topics.bat --list --bootstrap-server localhost:9092

https://github.com/lokeshgupta1981/Kafka-Tutorials/blob/master/4.%20stop%20kafka.bat
https://howtodoinjava.com/kafka/spring-boot-with-kafka/

List all the kafka topics:
cd kafka_2.12-2.5.1/
$ bin/kafka-topics.sh --list --zookeeper localhost:2181
![image](https://github.com/shivanarayana/SpringBootKafkaApp/assets/10083536/8e698671-6d30-4d47-a036-242306370cb0)

