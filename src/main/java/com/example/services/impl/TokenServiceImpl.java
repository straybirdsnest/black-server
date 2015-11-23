package com.example.services.impl;

import com.example.config.security.UserAuthentication;
import com.example.daos.UserRepo;
import com.example.models.User;
import com.example.services.TokenService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    public static final String TOKEN_CACHE_NAME = "tokenCache";
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    private static final Cache tokenCache = CacheManager.getInstance().getCache(TOKEN_CACHE_NAME);

    @Autowired UserRepo userRepo;

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        tokenCache.evictExpiredElements();
    }

    @NotNull
    @Override
    public String generateToken(User user) {
        UserAuthentication auth = new UserAuthentication(user);
        String token = UUID.randomUUID().toString();
        tokenCache.put(new Element(token, auth));
        return token;
    }

    @NotNull
    @Override
    public String generateTokenByPhone(String phone) {
        Integer id = userRepo.findUserIdByphone(phone);
        User user = userRepo.findOne(id);
        return generateToken(user);
    }

    @Nullable
    @Override
    public Object getObject(String token) {
        Element element = tokenCache.get(token);
        if (element == null) return null;
        return element.getObjectValue();
    }

    @Override
    public boolean isAvailable(String token) {
        return tokenCache.get(token) != null;
    }
}
