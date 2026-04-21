package com.example.userms_client;

import java.util.Objects;

public record UserDto(Long id, String name, String email) {
    public UserDto {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
    }
}
