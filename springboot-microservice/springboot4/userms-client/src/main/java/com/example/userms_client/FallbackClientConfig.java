package com.example.userms_client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class FallbackClientConfig {
    @Bean
    public UserClientInterface userClientInterface(RestClient.Builder builder) {
        // 1. Create the underlying RestClient
        RestClient restClient = builder.baseUrl("http://userms").build();

        // 2. Create the adapter
        RestClientAdapter adapter = RestClientAdapter.create(restClient);

        // 3. Build the factory with circuit breaker support enabled
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();

        return factory.createClient(UserClientInterface.class);
    }
}
