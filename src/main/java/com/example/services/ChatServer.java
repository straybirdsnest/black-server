package com.example.services;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class ChatServer {
	public static final int CHAT_SERVER_PORT = 8081;

	private SocketIOServer socketIOServer = null;

	public ChatServer(Configuration configuration) {
		configuration.setHostname("localhost");
		configuration.setOrigin("http://localhost:8080");
		configuration.setPort(CHAT_SERVER_PORT);
		socketIOServer = new SocketIOServer(configuration);

		socketIOServer.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient socketIOClient) {
				ChatObject chatObject = new ChatObject("Server", "user connected.");
				socketIOServer.getBroadcastOperations().sendEvent("chatevent", chatObject);
			}

		});

		socketIOServer.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(SocketIOClient socketIOClient) {
				ChatObject chatObject = new ChatObject("Server", "user disconnected.");
				socketIOServer.getBroadcastOperations().sendEvent("chatevent", chatObject);
			}
		});

		socketIOServer.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {

			@Override
			public void onData(SocketIOClient socketIOClient, ChatObject chatObject, AckRequest ackRequest)
					throws Exception {
				socketIOServer.getBroadcastOperations().sendEvent("chatevent", chatObject);
			}

		});
	}

	public void startServer() {
		if (socketIOServer != null) {
			socketIOServer.start();
		}
	}

	public void stopServer() {
		if (socketIOServer != null) {
			socketIOServer.stop();
		}
	}

	public SocketIOServer getSocketIOServer() {
		return socketIOServer;
	}

}
