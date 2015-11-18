package com.example.exceptions;

public class FriendMyselfException extends BusinessException {
    public FriendMyselfException(int userId) {
        super(String.format("用户 (id = %d) 试图和自己成为朋友", userId));
    }
}
