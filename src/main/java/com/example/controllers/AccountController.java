package com.example.controllers;

import com.example.daos.UserRepository;
import com.example.models.User;
import com.example.config.restful.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {
	
	static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    UserRepository repository;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(String username, String password){
		log.info(username + " " + password + "注册");
	}
	
	@RequestMapping(value = "/username-available", method = RequestMethod.GET)
	public boolean checkUsernameAvailable(String username) {
		return true;
	}

    @RequestMapping(value = "/api/token", method = RequestMethod.GET)
    @ResponseBody
    public String getToken(@RequestParam String username, @RequestParam String password) {

        List<User> result = repository.findByUsername(username);

        if (!result.isEmpty()){
            User u = result.get(0);
            if (u.getPassword().equals(password)) {
                return TokenUtils.encode(username);
            }
        }

        return "";
    }
}
