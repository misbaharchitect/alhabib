package com.example.airlinems.service;

import org.core.AirlineBookedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerService {
//    @KafkaListener(topics = "saga-topic", groupId = "a")
    public void consume(AirlineBookedEvent event) {
        System.out.println("**********************");
        System.out.println("**********************");
        System.out.println("**********************");
        System.out.println(event.getOrderId());
    }
}
