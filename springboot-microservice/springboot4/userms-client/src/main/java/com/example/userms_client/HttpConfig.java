package com.example.userms_client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpConfig {

    /**
     * Regular RestTemplate - NOT load balanced
     * Used for standard HTTP calls and infrastructure (Eureka, Config Server, etc.)
     * Should NOT be used for microservice-to-microservice calls
     */
//    @Bean
//    @LoadBalanced
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }

    @Bean
    @Primary
    public RestClient.Builder directRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient directRestClient(
            @Qualifier("directRestClientBuilder") RestClient.Builder builder) {
        return builder.build();
    }

    @Bean
    @LoadBalanced
    public RestClient.Builder lbRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @Primary
    public RestClient serviceRestClient(
            @Qualifier("lbRestClientBuilder") RestClient.Builder builder) {
        return builder.baseUrl("http://userms").build();
    }




}
