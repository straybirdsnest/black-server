package com.example.controllers;

import com.example.daos.UserRepo;
import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
public class AdminController {
    @Autowired UserRepo userRepo;

    @RequestMapping("/admin/users")
    public Set<User> getAllUsers() {
        Iterator<User> i = userRepo.findAll().iterator();
        Stream<User> s = StreamSupport.stream(Spliterators.spliteratorUnknownSize(i, Spliterator.ORDERED), false);
        return s.collect(Collectors.toSet());
    }
}
