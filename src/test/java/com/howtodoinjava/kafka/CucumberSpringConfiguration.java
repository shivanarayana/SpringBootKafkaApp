package com.howtodoinjava.kafka;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = SpringBootKafkaAppApplication.class) // This points to your main app
public class CucumberSpringConfiguration { }