package com.example.exceptions;

public class VcodeVerificationException extends BusinessException {
    public VcodeVerificationException(String phoneWithZone, String vcode) {
        super(String.format("短信验证码验证失败 [phone = %s, vode = %s]", phoneWithZone, vcode));
    }
}
