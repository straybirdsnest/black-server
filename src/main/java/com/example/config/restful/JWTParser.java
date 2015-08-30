package com.example.config.restful;

/**
 * Created by yy on 8/30/15.
 */
public class JWTParser {

    private String username;

    public JWTParser(String token) {
        // 解码
        username = token;
    }

    public Object getSub(){
        // 获得代表用户的类
        SpringUserDetailsAdapter user = new SpringUserDetailsAdapter(username);
        return user;
    }
}
