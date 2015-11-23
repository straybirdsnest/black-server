package com.example.services;

import com.example.models.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 服务器的 token 服务, 目前主要用于用户和图片
 */
public interface TokenService {
    @NotNull
    String generateToken(User user);

    @NotNull
    String generateTokenByPhone(String phone);

    @Nullable
    Object getObject(String token);

    boolean isAvailable(String token);
}
