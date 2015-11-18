package com.example.exceptions;

public class FocusMyselfException extends BusinessException {
    public FocusMyselfException(int userId) {
        super(String.format("用户 (id = %d) 关注了自己", userId));
    }
}
