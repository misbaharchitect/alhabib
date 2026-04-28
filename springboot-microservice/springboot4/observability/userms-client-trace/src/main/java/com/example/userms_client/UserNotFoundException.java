package com.example.userms_client;

public class UserNotFoundException extends RuntimeException {
    private final Long userId;

    public UserNotFoundException(String message, Long userId) {
        super(message);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
