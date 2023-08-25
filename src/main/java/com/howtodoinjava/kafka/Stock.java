package com.howtodoinjava.kafka;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Stock {

    private Integer stockId;

    private String stockName;

    private String stockDescription;
}
