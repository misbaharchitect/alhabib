package com.example.userms_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CallUserms.class);

    @ExceptionHandler(value = { UserNotFoundException.class })
    public ResponseEntity<ErrorDto> handleUserNotFounException(UserNotFoundException e) {
        LOGGER.error("UserId {} not found with Error: {}", e.getUserId(), e.getMessage());
        // Return a generic error message or view
        return ResponseEntity.status(404).body(new ErrorDto(1001, "User with id " + e.getUserId() + " not found"));
    }

    @ExceptionHandler(value = { BadRequestForUserIdException.class })
    public ResponseEntity<ErrorDto> handleBadRequestForUserIdException(BadRequestForUserIdException e) {
        LOGGER.error("Bad request for UserId {} with Error: {}", e.getUserId(), e.getMessage());
        // Return a generic error message or view
        return ResponseEntity.status(400).body(new ErrorDto(1002, "Bad request for User with id " + e.getUserId()));
    }

    @ExceptionHandler(value = { ServerFailedToProcessException.class })
    public ResponseEntity<ErrorDto> handleServerFailedToProcessException(ServerFailedToProcessException e) {
        LOGGER.error("Server Failed To Process with Error: {}", e.getMessage());
        // Return a generic error message or view
        return ResponseEntity.status(500).body(new ErrorDto(5001, "Server Failed To Process"));
    }

    @ExceptionHandler(value = { IllegalStateException.class })
    public ResponseEntity<ErrorDto> handleServerFailedToProcessException(IllegalStateException e) {
        LOGGER.error("IllegalStateException with Error: {}", e.getMessage());
        // Return a generic error message or view
        return ResponseEntity.status(500).body(new ErrorDto(5002, e.getMessage()));
    }
}
