package com.example.exception;

public class ClientErrorException extends Exception {
    public ClientErrorException(String message) {
        super(message);
    }
}
