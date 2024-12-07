package com.example.kafka_consumer_1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaConsumer1Application {

	@KafkaListener(topics = "studenttopic", groupId = "a")
	public void consume(String str) {
		System.out.println("Consumer received: " + str);
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumer1Application.class, args);
	}

}
