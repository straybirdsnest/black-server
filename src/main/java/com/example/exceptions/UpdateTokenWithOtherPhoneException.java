package com.example.exceptions;

public class UpdateTokenWithOtherPhoneException extends BusinessException {
    public UpdateTokenWithOtherPhoneException(int userId, String phone) {
        super(String.format("用户 (id = %d) 试图使用其他的手机号 (%s) 更新 token", userId, phone));
    }
}
