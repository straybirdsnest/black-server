package com.example.services;

public interface CurrentThreadUserService {

    void addUserIdToThreadMap(int userId);

    int getCurrentThreadUserId();
}
