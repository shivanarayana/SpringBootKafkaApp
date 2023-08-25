package com.howtodoinjava.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@Slf4j
public class StockEventController {

    @Autowired
    StockEventProducer stockEventProducer;

    @PostMapping("/api/stockevent")
    public ResponseEntity<StockEvent> postStockEvent(@RequestBody StockEvent stockEvent) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {

        //Invoke Kafka
        log.info("Invoking Kafka Producer: Begins");
        stockEvent.setStockEventId(StockEventType.NEW);
    }
}
