package com.example.exceptions;

public class RegistedWithExistedPhoneException extends BusinessException {
    public RegistedWithExistedPhoneException(String phone) {
        super(String.format("使用已注册的电话号码（%s）注册新用户", phone));
    }
}
