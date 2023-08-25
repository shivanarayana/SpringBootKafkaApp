package com.howtodoinjava.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StockEvent {

    private Integer stockEventId;
    private StockEventType stockEventType;

    @NonNull
    private Stock stock;


}
