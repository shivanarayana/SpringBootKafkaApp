# SpringBootKafkaApp
SpringBootKafkaAppApplication

Steps: 
Cd kafka\kafka_2.12-2.8.1\bin\windows

Zookeeper:

zookeeper-server-start.bat ..\..\config\zookeeper.properties
kafka-server-start.bat ..\..\config\server.properties

Inside windows folder

kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --replication-factor 1 --partitions 3

kafka-topics.bat --create --topic test --zookeeper localhost:9092 --replication-factor 1 --partitions 3

Inside kafka folder

kafka-console-producer.bat --broker-list localhost:9092 --topic test

Inside windows folder

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning

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

# Create a Kafka topic for data migration
bin/kafka-topics.sh --create --topic migration-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
