package com.example.models;

import com.example.daos.UserRepo;
import com.example.utils.Cryptor;

/**
 * Token 是由用户ID（int），经过 AES 加密的base64字符串
 */
public class Token {

    private User user;

    private UserRepo userRepo;

    public Token(User user, UserRepo userRepo) {
        this.user = user;
    }

    public Token(String token, UserRepo userRepo) {
        byte[] data = Cryptor.decrypt(token);
        int id = data[0];
        id = (id << 8) | data[1];
        id = (id << 8) | data[2];
        id = (id << 8) | data[3];
        user = userRepo.findOne(id);
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        int id = user.getId();
        byte[] bytes = new byte[]{
                (byte) (id >>> 24),
                (byte) (id >>> 16),
                (byte) (id >>> 8),
                (byte) id};
        return Cryptor.encrypt(bytes);
    }
}
