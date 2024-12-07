package com.example.kafka_producer;

import com.example.common.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderApi {

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @PostMapping("/orders")
    public String send(Order order) {
        System.out.println("received order");
        kafkaTemplate.send("ordertopic", order.getName(), order);
        return "Message sent: " + order.getName();
    }
}
