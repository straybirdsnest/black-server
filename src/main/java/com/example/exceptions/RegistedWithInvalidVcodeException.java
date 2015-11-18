package com.example.exceptions;

public class RegistedWithInvalidVcodeException extends BusinessException {
    public RegistedWithInvalidVcodeException(String vcode) {
        super(String.format("使用无效的短信验证码（%s）注册新用户", vcode));
    }
}
