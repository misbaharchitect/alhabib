package com.example.orderms_client;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class OrdermsService implements OrdermsServiceInterface {

    private final RestClient restClient;
    private final CircuitBreaker circuitBreaker;

    public OrdermsService(@Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder, CircuitBreakerRegistry circuitBreakerRegistry) {
        this.restClient = restClientBuilder.build();
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("ordermsService");
    }

    public String callOrdermsHelloWorld() {
        try {
            return circuitBreaker.executeSupplier(() -> restClient.get()
                    .uri("http://orderms/hello-world")
                    .retrieve()
                    .body(String.class));
        } catch (Exception e) {
            return fallbackHelloWorld(e);
        }
    }

    public String fallbackHelloWorld(Throwable t) {
        return "sending fallback";
    }
}
