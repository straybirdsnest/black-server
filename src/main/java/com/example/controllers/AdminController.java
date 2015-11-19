package com.example.controllers;

import com.example.daos.ImageRepo;
import com.example.daos.UserRepo;
import com.example.exceptions.MyCustomError;
import com.example.exceptions.MyCustomException;
import com.example.models.Image;
import com.example.models.User;
import com.example.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
public class AdminController {
    @Autowired UserRepo userRepo;

    @Autowired ImageRepo imageRepo;

    @Autowired TokenService tokenService;

    @RequestMapping("/admin/users")
    public Set<User> getAllUsers() {
        Iterator<User> i = userRepo.findAll().iterator();
        Stream<User> s = StreamSupport.stream(Spliterators.spliteratorUnknownSize(i, Spliterator.ORDERED), false);
        Set<User> users = s.collect(Collectors.toSet());
        return users;
    }

    @RequestMapping("/admin/a")
    public Image a() throws Exception{
        throw new MyCustomException();
    }

    @RequestMapping("/admin/b")
    public Image b() throws Exception{
        throw new MyCustomError();
    }

    @RequestMapping("/admin/images")
    public Set<Image> getAllImages() {
        Iterator<Image> i = imageRepo.findAll().iterator();
        Stream<Image> s = StreamSupport.stream(Spliterators.spliteratorUnknownSize(i, Spliterator.ORDERED), false);
        Set<Image> images = s.collect(Collectors.toSet());
        return images;
    }

    @RequestMapping("/admin/tokens")
    public Map<String, String> getAllTokens() {
        Iterator<User> i = userRepo.findAll().iterator();
        Map<String, String> result = new HashMap<>();
        while (i.hasNext()) {
            User u = i.next();
            result.put(u.getProfile().getPhone(), tokenService.generateToken(u));
        }
        return result;
    }
}
