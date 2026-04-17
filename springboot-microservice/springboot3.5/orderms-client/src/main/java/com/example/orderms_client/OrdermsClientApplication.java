package com.example.orderms_client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrdermsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdermsClientApplication.class, args);
    }
}
