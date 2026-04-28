package com.example.userms_client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import io.micrometer.observation.ObservationRegistry;

@Configuration
public class HttpConfig {

    @Autowired
    private ObservationRegistry observationRegistry;

    @Bean
    public SimpleClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000); // 2 seconds
        factory.setReadTimeout(5000); // 5 seconds
        return factory;
    }

    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder().requestFactory(clientHttpRequestFactory()).observationRegistry(observationRegistry);
    }

    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder().requestFactory(clientHttpRequestFactory()).observationRegistry(observationRegistry);
    }

}
