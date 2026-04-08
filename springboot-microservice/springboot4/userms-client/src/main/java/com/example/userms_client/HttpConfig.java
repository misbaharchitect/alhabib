package com.example.userms_client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class HttpConfig {

    /**
     * RestClient bean with load balancing support using EurekaClient
     * This bean will automatically load balance requests across instances registered in Eureka
     */
    @Bean
    @LoadBalanced
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(@Qualifier("restClientBuilder") RestClient.Builder builder) {
        return builder
                .build();
    }
}
