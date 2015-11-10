package com.example.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlingController {
    static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "您的请求已经傲娇~~")
    public void handleMissingServletRequestParameter(MissingServletRequestParameterException e) {
        logger.warn("客户端请求参数不完整", e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "您的请求已经傲娇~~")
    public void handleException(Exception e) {
        logger.warn("服务器发生异常，这种消息怎么可以外泄！", e);
    }

    // TODO 还的自定义 400 的错误返回页面，避免暴露服务器的内部实现

}
