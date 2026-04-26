package com.example.kafka_producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KafkaProducerApplication {

	@Autowired
    KafkaTemplate<String, Student> kafkaTemplate;

	@GetMapping("/students/{name}")
	public String addStudent(@PathVariable String name){
		System.out.println("str: " + name);
		Student student = new Student(name);
		kafkaTemplate.send("studenttopic", name, student);
		return name + " is published";
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

}
