package com.example.userms_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/client-users")
public class CallUserms {

    @Autowired
    private RestClient restClient;

    @GetMapping
    public List getUsers() {
        // Make a load-balanced request using Eureka service name
        return restClient.get()
                .uri("http://userms/users")
                .retrieve()
                .body(List.class);
    }
}
