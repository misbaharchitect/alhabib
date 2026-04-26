package com.example.kafka_consumer_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaConsumer2Application {

	public static void main(String[] args) {
		SpringApplication.run(KafkaConsumer2Application.class, args);
	}

}
