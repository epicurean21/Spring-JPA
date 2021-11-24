package com.example.springjpa.error;

public class BusinessException extends RuntimeException {
    public BusinessException() {

    }

    public BusinessException(String message) {
        super(message);
    }
}
