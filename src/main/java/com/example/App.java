package com.example;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.dev.DevHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class App implements ApplicationListener<ApplicationEvent>, ApplicationContextAware {
    public static final String CFG_TOKEN_LIFETIME = "blackserver.token.lifetime";
    public static final String CFG_CHAT_HOST = "blackserver.chat.host";
    public static final String CFG_CHAT_PORT = "blackserver.chat.port";
    private static final Logger log = LoggerFactory.getLogger(App.class);
    public static int tokenLifetime;

    @Autowired
    private SocketIOServer chatServer;

    public static void main(String[] args) {
        DevHelper.initDb(args);
        SpringApplication.run(App.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            log.info("Spring 容器初始化完毕，开始启动聊天服务器");
            chatServer.start();
        } else if (event instanceof ContextStartedEvent) {
            log.info("======== ContextStartedEvent ========");
        } else if (event instanceof ContextStoppedEvent) {
            log.info("======== ContextStoppedEvent ========");
        } else if (event instanceof ContextClosedEvent) {
            log.info("Spring 容器已经关闭，开始关闭聊天服务器");
            chatServer.stop();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Environment env = applicationContext.getEnvironment();
        tokenLifetime = env.getProperty(CFG_TOKEN_LIFETIME, Integer.class, 180);
    }
}
