package com.example.config;

import com.example.services.ChatServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatServerConfig {

	@Bean(initMethod = "startServer", destroyMethod = "stopServer")
	public ChatServer chatServer() {
		return new ChatServer(configuration());
	}

	@Bean
	public com.corundumstudio.socketio.Configuration configuration() {
		return new com.corundumstudio.socketio.Configuration();
	}

}
