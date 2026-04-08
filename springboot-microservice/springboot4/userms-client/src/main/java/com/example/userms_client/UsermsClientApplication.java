package com.example.userms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UsermsClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsermsClientApplication.class, args);
	}

}
