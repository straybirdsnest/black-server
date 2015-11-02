package com.example.services;

import com.example.daos.UserRepo;
import com.example.models.User;
import com.example.utils.Cryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 用于生成 Token 字符串
 * Token 是由用户ID（int, 4 个字节），经过 AES 加密的base64字符串
 * 为了简单，暂且这样定
 */
@Component
public class TokenService {

    @Autowired
    UserRepo userRepo;

    public String generateToken(User user){
        int id = user.getId();
        byte[] bytes = new byte[]{
                (byte) (id >>> 24),
                (byte) (id >>> 16),
                (byte) (id >>> 8),
                (byte) id};
        return Cryptor.encrypt(bytes);
    }

    public User getUser(String token){
        byte[] data = Cryptor.decrypt(token);
        int id = data[0];
        id = (id << 8) | data[1];
        id = (id << 8) | data[2];
        id = (id << 8) | data[3];
        return userRepo.findOne(id);
    }



}
