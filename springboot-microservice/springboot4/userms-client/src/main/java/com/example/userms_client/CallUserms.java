package com.example.userms_client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client-users")
public class CallUserms {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallUserms.class);

    private final RestClient restClient;

    public CallUserms(@Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @GetMapping
    @CircuitBreaker(name = "usermsclient", fallbackMethod = "getUsersFallback")
    public ResponseEntity<List<UserDto>> getUsers() {
        LOGGER.info("Making request to userms to fetch users...");
        List<UserDto> usersDto = restClient.get()
                .uri("http://userms/users")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        assert usersDto != null;
        LOGGER.info("Received response from userms: {} users", usersDto.size());
        return ResponseEntity.ok(usersDto);
    }

    public ResponseEntity<List<UserDto>> getUsersFallback(CallNotPermittedException ex) {
        LOGGER.error("OPEN - CallNotPermittedException Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(List.of(new UserDto(101L, "John Doe", "john@email.com")));
    }

    public ResponseEntity<List<UserDto>> getUsersFallback(Exception ex) {
        LOGGER.error("HALF-OPEN - Exception Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(List.of(new UserDto(102L, "Jane Doe", "jane@email.com")));
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "usermsclient", fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        LOGGER.info("Making request to userms to fetch user with id {}", id);
        ResponseEntity<UserDto> userFound = restClient.get()
                .uri("http://userms/users/{id}", id)
                .retrieve()
                .toEntity(UserDto.class);
        LOGGER.info("Received response from userms for user with id {} ", id);
        return userFound;
    }

    public ResponseEntity<UserDto> getUserByIdFallback(CallNotPermittedException ex) {
        LOGGER.error("OPEN - CallNotPermittedException Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(new UserDto(101L, "John Doe", "john@email.com"));
    }

    public ResponseEntity<UserDto> getUserByIdFallback(Exception ex) {
        LOGGER.error("OPEN - CallNotPermittedException Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(new UserDto(102L, "Jane Doe", "jane@email.com"));
    }

    @GetMapping("/{id}/v2")
    public ResponseEntity<?> getUserByIdV2(@PathVariable Long id) {
        LOGGER.info("V2-Making request to userms to fetch user with id {}", id);
        try {

            ResponseEntity<?> user = restClient.get()
                    .uri("http://userms/users/{id}", id)
                    .retrieve()
                    .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(),
                            (request, response) -> {
                                LOGGER.warn("V2-Received non-success status {} from userms for user with id {} ", response.getStatusCode(), id);
                                throw new UserNotFounException("User with id " + id + " not found");
                            }).toEntity(UserDto.class);
            LOGGER.info("V2-Received response from userms for user with id {} ", id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            LOGGER.error("Error calling userms for userId: {}", id, e);
            return ResponseEntity.status(503)
                    .body(Map.of("error", "Service unavailable", "message", e.getMessage(), "type", e.getClass().getSimpleName()));
        }
    }
}

