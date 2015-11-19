package com.example;

import com.example.config.ApplicationLifecycleManager;
import com.example.config.ApplicationProperties;
import com.example.dev.DevHelper;
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

    public static void main(String[] args) {
        // 服务器使用 UTC 时间
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));
        // 初始化数据库
        DevHelper.initDb(args);
        // 启动服务器
        SpringApplication app = new SpringApplication(App.class);
        app.addListeners(new ApplicationLifecycleManager());
        app.run(args);
    }
}
