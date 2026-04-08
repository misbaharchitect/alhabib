package com.example.userms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class UsermsApplication {

	@GetMapping("/salam")
	public String getSalam() {
		return "Salam World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(UsermsApplication.class, args);
	}

}
