package com.example.exceptions;

public class SystemError extends RuntimeException {
    public SystemError(String message, Throwable cause) {
        super(message, cause);
    }
}
