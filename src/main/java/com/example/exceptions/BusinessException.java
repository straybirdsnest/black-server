package com.example.exceptions;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super("【业务逻辑异常】" + message);
    }
}
