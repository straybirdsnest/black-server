package com.example.config;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.example.services.ChatObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ChatServerConfig {

	/*@Bean(initMethod = "startServer", destroyMethod = "stopServer")
    public ChatServer chatServer() {
		return new ChatServer(configuration());
	}

	@Bean
	public com.corundumstudio.socketio.Configuration configuration() {
		return new com.corundumstudio.socketio.Configuration();
	}*/

    @Autowired
    Environment env;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration cfg =
                new com.corundumstudio.socketio.Configuration();
        //cfg.setHostname(env.getProperty("blackserver.chat.host", "localhost"));
        cfg.setHostname(env.getProperty("blackserver.chat.host"));
        //cfg.setPort(env.getProperty("blackserver.chat.port", Integer.class, 8081));
        cfg.setPort(env.getProperty("blackserver.chat.port", Integer.class));

        final SocketIOServer server = new SocketIOServer(cfg);

        server.addConnectListener(new ConnectListener() {

            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                ChatObject chatObject = new ChatObject("Server", "user connected.");
                server.getBroadcastOperations().sendEvent("chatevent", chatObject);
            }

        });
        server.addDisconnectListener(new DisconnectListener() {

            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                ChatObject chatObject = new ChatObject("Server", "user disconnected.");
                server.getBroadcastOperations().sendEvent("chatevent", chatObject);
            }
        });
        server.addEventListener("chatevent", ChatObject.class, new DataListener<ChatObject>() {

            @Override
            public void onData(SocketIOClient socketIOClient, ChatObject chatObject, AckRequest ackRequest)
                    throws Exception {
                server.getBroadcastOperations().sendEvent("chatevent", chatObject);
            }

        });
        return server;
    }

}
