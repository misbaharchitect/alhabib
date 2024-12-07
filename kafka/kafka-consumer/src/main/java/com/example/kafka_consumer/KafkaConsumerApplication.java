package com.example.kafka_consumer;

import com.example.common.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaConsumerApplication {

	@KafkaListener(topics = "ordertopic", groupId = "group1")
	void listener(Order order) {
		System.out.println("Received message in group1: " + order);
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumerApplication.class, args);
	}

}
