package com.example.services;

import com.example.models.User;
import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    @NotNull
    User getCurrentUser();

    int getCurrentUserId();

    boolean currentUserIsHisFriend(int userId);

    boolean currentUserIsHisFan(int userId);

    boolean currentUserIsHisFocus(int userId);

    @NotNull
    User createAndSaveUser(String phone, HttpServletRequest request);

    @NotNull
    User updateUser(User newUser);

    void deleteCurrentUser();

    @NotNull
    User getUserById(int id);

    boolean isPhoneExisted(String phone);
}
