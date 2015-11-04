package com.example.services;

import com.example.daos.UserRepo;
import com.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;

    private ConcurrentHashMap<Long, Integer> userMap = new ConcurrentHashMap<>();

    public User getCurrentUser() {
        long threadId = Thread.currentThread().getId();
        Integer userId = userMap.get(threadId);
        User user = userRepo.findOne(userId);
        return user;
    }

    public void addUser(int userId) {
        long threadId = Thread.currentThread().getId();
        userMap.put(threadId, userId);
    }
}
