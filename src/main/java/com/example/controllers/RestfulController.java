package com.example.controllers;

import com.example.daos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 这个控制器主为服务器的 Restful API 提供基础支持
 * Created by yy on 9/14/15.
 */
@RestController
public class RestfulController {

    @Autowired
    UserRepository userRepo;

    /**
     * /api/token 的请求会经过 Spring Security 默认的
     * UsernamePasswordAuthenticationFilter 验证
     * 使用 Spring Security 提供的最强的密码加密手段 BCryptPasswordEncoder
     *
     * @return 如果你提供的用户名和密码通过了验证，则会返回内容就是一个代表 token 的
     * 字符串，有效期为24小时。如果你没有通过验证会得到 401 Unauthorized Error
     */
    @RequestMapping(value = "/api/token", method = RequestMethod.GET)
    public String getToken() {
        return "这就是token";
    }



}
