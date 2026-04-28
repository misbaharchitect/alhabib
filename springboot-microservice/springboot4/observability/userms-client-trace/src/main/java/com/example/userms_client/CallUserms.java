package com.example.userms_client;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/client-users")
public class CallUserms {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallUserms.class);

    private final RestClient restClient;

    public CallUserms(@Qualifier("loadBalancedRestClientBuilder") RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @GetMapping
    @Observed
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

    private ResponseEntity<List<UserDto>> getUsersFallback(CallNotPermittedException ex) {
        LOGGER.error("OPEN - getUsersFallback CallNotPermittedException Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(List.of(new UserDto(101L, "John Doe", "john@email.com")));
    }

    private ResponseEntity<List<UserDto>> getUsersFallback(Exception ex) {
        LOGGER.error("HALF-OPEN - getUsersFallback Exception Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(List.of(new UserDto(102L, "Jane Doe", "jane@email.com")));
    }

    @GetMapping("/{id}/v2")
    @Observed
    @CircuitBreaker(name = "userByIdclient", fallbackMethod = "getUserByIdFallback")
    public ResponseEntity<UserDto> getUserByIdV2(@PathVariable Long id) {
        LOGGER.info("V2-Making request to userms to fetch user with id {}", id);
        ResponseEntity<UserDto> userFound = restClient.get()
                .uri("http://userms/users/{id}", id)
                .retrieve()
                .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(),
                        (request, response) -> {
                            LOGGER.error("V2-Received non-success(404) status {} from userms for user with id {} ", response.getStatusCode(), id);
                            throw new UserNotFoundException("User not found", id);
                        })
                .onStatus(HttpStatusCode::is4xxClientError,
                        (request, response) -> {
                            LOGGER.error("V2-Received non-success(404) status {} from userms for user with id {} ", response.getStatusCode(), id);
                            throw new BadRequestForUserIdException("User not found", id);
                        })
                .onStatus(HttpStatusCode::is5xxServerError,
                        (request, response) -> {
                            LOGGER.error("V2-Received non-success(5xx) status {} from userms for user with id {} ", response.getStatusCode(), id);
                            throw new ServerFailedToProcessException("Server error from userms");
                        })
                .toEntity(UserDto.class);
        LOGGER.info("V2-Received response from userms for user with id {} ", id);
        return userFound;
    }

    private ResponseEntity<UserDto> getUserByIdFallback(CallNotPermittedException ex) {
        LOGGER.error("OPEN - getUserByIdFallback CallNotPermittedException Fallback is invoked");
        // Add your logic to handle open-state of circuit-breaker
        return ResponseEntity.ok(new UserDto(101L, "John Doe", "john@email.com"));
    }

}
