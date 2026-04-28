package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UsermsApplication {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }

	public static void main(String[] args) {
		SpringApplication.run(UsermsApplication.class, args);
	}

}
