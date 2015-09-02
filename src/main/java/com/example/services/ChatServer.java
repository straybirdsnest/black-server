package com.example.services;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class ChatServer {
	public static final int CHAT_SERVER_PORT = 8081;
	
	private SocketIOServer socketIOServer = null;
	
	public ChatServer(Configuration configuration){
		configuration.setHostname("localhost");
	    configuration.setPort(CHAT_SERVER_PORT);
	    socketIOServer = new SocketIOServer(configuration);
	}
	
	public void startServer(){
		if(socketIOServer != null){
			socketIOServer.start();
		}
	}
	
	public void stopServer(){
		if(socketIOServer != null){
			socketIOServer.stop();
		}
	}
	
	public SocketIOServer getSocketIOServer(){
		return socketIOServer;
	}

}
