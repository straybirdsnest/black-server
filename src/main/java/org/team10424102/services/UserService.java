package org.team10424102.services;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.team10424102.config.security.UserAuthentication;
import org.team10424102.models.User;

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
