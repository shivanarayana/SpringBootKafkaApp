package com.howtodoinjava.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class KafkaProducerController
{
    private final KafKaProducerService producerService;

    @Autowired
    public KafkaProducerController(KafKaProducerService producerService)
    {
        this.producerService = producerService;
    }

    @GetMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message)
    {
        this.producerService.sendMessage(message);
    }
}