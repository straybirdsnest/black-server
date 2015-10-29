package com.example;

import com.corundumstudio.socketio.SocketIOServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
public class App implements ApplicationListener<ApplicationEvent> {

    private static final Logger log = LoggerFactory.getLogger(App.class);
    @Autowired
    SocketIOServer chatServer;

	public static void main(String[] args) {
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
}
