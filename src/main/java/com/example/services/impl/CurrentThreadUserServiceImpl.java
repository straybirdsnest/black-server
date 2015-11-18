package com.example.services.impl;

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
            String errorMsg = String.format("当前请求线程（%d）没有对应的用户 ID", threadId);
            RuntimeException e = new RuntimeException(errorMsg);
            logger.error(errorMsg, e);
            throw e;
        }
        return userId;
    }
}
