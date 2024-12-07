package com.example.kafka_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class KafkaProducerApplication {

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping("/students/{str}")
	public String addStudent(@PathVariable String str){
		System.out.println("str: " + str);
		kafkaTemplate.send("studenttopic", str, str);
		return str + " is published";
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

}
