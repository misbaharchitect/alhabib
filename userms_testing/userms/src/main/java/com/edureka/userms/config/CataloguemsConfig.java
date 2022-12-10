package com.edureka.userms.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CataloguemsConfig {

    /*@Bean(name = "catalogueRestTemplate")
    @LoadBalanced
    public RestTemplate getRestTemplate(RestTemplateBuilder rtb) {
        return rtb.build();
    }*/
}
