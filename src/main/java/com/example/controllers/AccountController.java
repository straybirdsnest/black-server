package com.example.controllers;

import com.example.daos.UserRepository;
import com.example.models.User;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
public class AccountController {

    static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserRepository repository;

    @Autowired
    JsonWebEncryption jwe;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String password) {

        User u = repository.findOneByUsername(username);

        if (u == null) {
            repository.save(new User(username, password));
            log.info("新用户 " + username + " 注册成功");
            return "注册成功";
        }

        return "注册失败";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/username-available", method = RequestMethod.GET)
    public boolean checkUsernameAvailable(String username) {
        return true;
    }

    @RequestMapping(value = "/api/token", method = RequestMethod.GET)
    @ResponseBody
    public String getToken(@RequestParam String username, @RequestParam String password) {
        User u = repository.findOneByUsername(username);
        if (u == null) return "";
        if (u.getPassword().equals(password)) {
            // 从用户信息生成 token
            jwe.setPayload(u.getId() + (new Date()).toString());
            try {
                return jwe.getCompactSerialization();
            } catch (JoseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }
}
