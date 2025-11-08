package com.howtodoinjava.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties; // <-- 1. CRITICAL IMPORT

@SpringBootApplication
@EnableConfigurationProperties(ExternalServiceProperties.class) // <-- 2. CRITICAL HOOK
public class SpringBootKafkaAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaAppApplication.class, args);
	}

}