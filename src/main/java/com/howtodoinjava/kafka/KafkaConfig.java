package com.howtodoinjava.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer; // Not needed for String

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    // 1. Define ProducerFactory for <String, String>
    @Bean
    public ProducerFactory<String, String> producerFactory() { // Changed Object to String
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 🚨 IMPORTANT: Use StringSerializer for the value, not JsonSerializer
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }

    // 2. Define KafkaTemplate for <String, String>
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) { // Changed Item to String
        return new KafkaTemplate<>(producerFactory);
    }
}