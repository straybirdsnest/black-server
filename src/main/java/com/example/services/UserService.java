package com.example.services;

import com.example.config.security.UserAuthentication;
import com.example.models.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    @NotNull
    User getCurrentUser();

    int getCurrentUserId();

    boolean isSecondsFriend(int firstUid, int secondUid);

    boolean isSecondsFan(int firstUid, int secondUid);

    boolean isSecondsFocus(int firstUid, int secondUid);

    @NotNull
    User createAndSaveUser(String phone, HttpServletRequest request);

    @NotNull
    User updateUser(User newUser);

    void deleteCurrentUser();

    @NotNull
    User getUserById(int id);

    boolean isPhoneExisted(String phone);

    @NotNull
    String generateToken(User user);

    @Nullable
    UserAuthentication getUserAuthenticationFromToken(String token);

    boolean isTokenValid(String token);

    User findByPhone(String phone);
}
