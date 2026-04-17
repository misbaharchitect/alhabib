package com.example.userms_client;

import org.springframework.stereotype.Component;

@Component
public class UserClientFallback implements UserClientInterface {

    @Override
    public Object getUsers(Long id) {
        return "Sending from Native fallback";
    }
}
