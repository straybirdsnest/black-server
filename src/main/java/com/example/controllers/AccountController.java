package com.example.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	
	static final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(String username, String password){
		log.info(username + " " + password + "注册");
	}
	
	@RequestMapping(value = "/username-available", method = RequestMethod.GET)
	public boolean checkUsernameAvailable(String username) {
		return true;
	}
}
