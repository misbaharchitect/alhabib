package com.example.userms_client;

public class BadRequestForUserIdException extends RuntimeException {
    private final Long userId;

    public BadRequestForUserIdException(String message, Long userId) {
        super(message);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
