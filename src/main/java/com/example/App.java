package com.example;

import com.example.config.ApplicationLifecycleManager;
import com.example.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.TimeZone;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class App {
    public static final String NAME = "blackserver";
    public static final String DEFAULT_AVATAR_TAG = "默认用户头像";
    public static final String DEFAULT_BACKGROUND_TAG = "默认用户背景";
    public static final String DEFAULT_COVER_TAG = "默认活动封面";
    public static final String API_USER = "/api/user";
    public static final String API_TOKEN = "/api/token";
    public static final String API_AVAILABILITY = "/api/availability";
    public static final String API_FOCUSES = "/api/focuses";
    public static final String API_FANS = "/api/fans";
    public static final String API_FRIENDS = "/api/friends";
    public static final String API_IMAGE = "/api/image";
    public static SpringApplication handle;

    public static void main(String[] args) {
        // 服务器使用 UTC 时间
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        // 启动服务器
        handle = new SpringApplication(App.class);
        handle.addListeners(new ApplicationLifecycleManager());
        handle.run(args);
    }
}
