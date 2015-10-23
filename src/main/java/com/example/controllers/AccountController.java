package com.example.controllers;

import com.example.daos.UserGroupRepository;
import com.example.daos.UserRepository;
import com.example.models.User;
import com.example.models.UserGroup;
import com.example.services.UserService;
import com.example.utils.EncodePasswordUtil;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;

@RestController
public class AccountController {

    static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserRepository repository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    JsonWebEncryption jwe;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String username, String password)
    {
        User user = repository.findOneByUsername(username);
        UserGroup userGroup = userGroupRepository.findOneByGroupName("ROLE_USER");
        if (user == null && userGroup != null) {
            user = new User(username, password);
            ArrayList<UserGroup> userGroups = new ArrayList<>();
            userGroups.add(userGroup);
            user.setUserGroups(userGroups);
            repository.save(user);
            return "注册成功";
        }
        return "";
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
        User user = repository.findOneByUsername(username);
        if (user == null) return "";
        if (EncodePasswordUtil.matches(password, user.getPassword())) {
            // 从用户信息生成 token
            jwe.setPayload(user.getUserId() + (new Date()).toString());
            try {
                return jwe.getCompactSerialization();
            } catch (JoseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String username, String password){
        User user = repository.findOneByUsername(username);
        if(user != null && EncodePasswordUtil.matches(password,user.getPassword())){
            return "登录成功";
        }
        return "";
    }
}
