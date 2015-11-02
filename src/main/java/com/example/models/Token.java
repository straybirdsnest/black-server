package com.example.models;

/**
 * Token 是由用户ID（int），经过 AES 加密的base64字符串
 */
public class Token {

    private User user;

    public Token(User user){
        this.user = user;
    }

    public Token(String token){

    }

    public User getUser(){
        return user;
    }

    @Override
    public String toString() {

        return null;
    }
}
