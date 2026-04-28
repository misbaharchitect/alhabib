package com.example.userms_client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.observation.annotation.Observed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/client-users2")
public class CallUserms2 {

    private final RestClient restClient;

    public CallUserms2(@Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @CircuitBreaker(name = "helloService", fallbackMethod = "helloFallback")
    @GetMapping("/call")
    @Observed
    public String hello() {
        return restClient.get()
                .uri("http://orderms/hello-world")
                .retrieve()
                .body(String.class);
    }

    public String helloFallback(CallNotPermittedException t) {
        return "CallNotPermittedException sending fallback from /hello";
    }

    public String helloFallback(Throwable t) {
        return "Throwable sending fallback from /hello";
    }
}
