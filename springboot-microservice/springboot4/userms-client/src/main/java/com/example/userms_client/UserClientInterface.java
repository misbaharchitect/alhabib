package com.example.userms_client;

import org.springframework.cloud.client.circuitbreaker.httpservice.HttpServiceFallback;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/myusers")
@HttpServiceFallback(UserClientFallback.class)
public interface UserClientInterface {
    @GetExchange("/{id}")
    Object getUsers();
}
