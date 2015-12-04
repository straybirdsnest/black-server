package com.example.exceptions;

public class UserNotFoundException extends SystemException {
    public UserNotFoundException(int userId) {
        super(String.format("找不到用户 (id = %d)", userId));
    }
}
