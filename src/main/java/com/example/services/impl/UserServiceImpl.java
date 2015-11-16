package com.example.services.impl;

import com.example.daos.UserRepo;
import com.example.models.User;
import com.example.services.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    private ConcurrentHashMap<Long, Integer> userMap = new ConcurrentHashMap<>();

    @NotNull
    public User getCurrentUser() {
        long threadId = Thread.currentThread().getId();
        Integer userId = userMap.get(threadId);
        return userRepo.findOne(userId);
    }

    public void addUser(int userId) {
        long threadId = Thread.currentThread().getId();
        userMap.put(threadId, userId);
    }

    @Override
    public int getCurrentUserId() {
        return 0;
    }

    @Override
    public boolean currentUserIsHisFriend(int UserId) {
        return false;
    }

    @Override
    public boolean currentUserIsHisFan(int UserId) {
        return false;
    }

    @Override
    public boolean currentUserIsHisFollwing(int UserId) {
        return false;
    }
}
