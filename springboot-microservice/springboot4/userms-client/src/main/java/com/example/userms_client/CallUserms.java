package com.example.userms_client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client-users")
public class CallUserms {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallUserms.class);

    private final RestClient restClient;
    private final UserClientInterface uci;


    public CallUserms(RestClient restClient, UserClientInterface uci) {
        this.restClient = restClient;
        this.uci = uci;
    }

    @GetMapping("/native")
    public Object getNative() {
        return uci.getUsers();
    }
    @GetMapping
//    @CircuitBreaker(name = "usermsclient", fallbackMethod = "usermsfallback")
    public ResponseEntity getUsers() {
        try {
            LOGGER.info("Making request to userms to fetch users...");
            List<UserDto2> usersDto = restClient.get()
                    .uri("http://userms/users")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            assert usersDto != null;
            LOGGER.info("Received response from userms: {} users", usersDto.size());
            return ResponseEntity.ok(usersDto);
        } catch (Exception e) {
            LOGGER.error("Error calling userms: ", e);
            return ResponseEntity.status(503)
                    .body(Map.of("error", "Service unavailable", "message", e.getMessage(), "type", e.getClass().getSimpleName()));
        }
    }

    public ResponseEntity usermsfallback(Exception ex) {
        LOGGER.error("Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(new String[]{"1", "2", "3"});
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        LOGGER.info("Making request to userms to fetch user with id {}", id);
        try {
            ResponseEntity<UserDto> user = restClient.get()
                    .uri("http://userms/users/{id}", id)
                    .retrieve()
                    .toEntity(UserDto.class);
            LOGGER.info("Received response from userms for user with id {} ", id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            LOGGER.error("Error calling userms for userId: {}", id, e);
            return ResponseEntity.status(503)
                    .body(Map.of("error", "Service unavailable", "message", e.getMessage(), "type", e.getClass().getSimpleName()));
        }
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
                    }).toEntity(UserDto.class)
                    ;
            LOGGER.info("V2-Received response from userms for user with id {} ", id);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            LOGGER.error("Error calling userms for userId: {}", id, e);
            return ResponseEntity.status(503)
                    .body(Map.of("error", "Service unavailable", "message", e.getMessage(), "type", e.getClass().getSimpleName()));
        }
    }
}

