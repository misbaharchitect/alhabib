package com.example.userms_client;

public class ServerFailedToProcessException extends RuntimeException {

    public ServerFailedToProcessException(String message){
        super(message);
    }

}
