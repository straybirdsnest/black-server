package com.example.services.impl;

import com.example.models.User;
import com.example.services.CurrentThreadUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class CurrentThreadUserServiceImpl implements CurrentThreadUserService {
    private static final Logger logger = LoggerFactory.getLogger(CurrentThreadUserServiceImpl.class);

    private ConcurrentHashMap<Long, Integer> userIdMap = new ConcurrentHashMap<>();

    @Override
    public void addUserIdToThreadMap(int userId) {
        long threadId = Thread.currentThread().getId();
        userIdMap.put(threadId, userId);
    }

    public int getCurrentThreadUserId() {
        Long threadId = Thread.currentThread().getId();
        Integer userId = userIdMap.get(threadId);
        if (userId == null) {
            //logger.error("未能找到当前线程 (id = %d) 对应的 UserId", threadId);
            //throw new SystemError(String.format("未能找到当前线程 (id = %d) 对应的 UserId", threadId), null);
            return User.UID_SYSTEM;
        }
        return userId;
    }
}
