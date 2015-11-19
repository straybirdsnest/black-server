package com.example.exceptions;

public class SystemError extends Error {
    public SystemError(String message, Throwable cause) {
        super(message, cause);
    }
}
