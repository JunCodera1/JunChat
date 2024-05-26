package com.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.model.ModelLogin;
import com.model.ModelMessage;
import com.model.ModelRegister;
import com.model.ModelUserAccount;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTextArea;

public class Service {

    private static Service instance;
    private SocketIOServer server;
    private ServiceUser serviceUser;
    private JTextArea textArea;
    private final int PORT_NUMBER = 9999;

    public static Service getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }

    private Service(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUser();
    }

    public void startServer() {
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("One client connected\n");
            }
        });
        server.addEventListener("register", ModelRegister.class, new DataListener<ModelRegister>() {
            @Override
            public void onData(SocketIOClient sioc, ModelRegister t, AckRequest ar) throws Exception {
                ModelMessage message = serviceUser.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if (message.isAction()) {
                    textArea.append("User has Register :" + t.getUserName() + " Pass :" + t.getPassword() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (ModelUserAccount) message.getData());
                }
            }
        });
       server.addEventListener("login", ModelLogin.class, new DataListener<ModelLogin>() {
            @Override
            public void onData(SocketIOClient sioc, ModelLogin loginData, AckRequest ackRequest) throws Exception {
                try {
                    // Attempt to log in the user with the provided credentials
                    ModelUserAccount userAccount = serviceUser.login(loginData);

                    // Check if the userAccount is null
                    if (userAccount != null) {
                        // Proceed with operations on userAccount safely
                        int userID = userAccount.getUserID(); // Example of accessing userID
                        // Send acknowledgment with success and user account data
                        ackRequest.sendAckData(true, userAccount);
                    } else {
                        // Send acknowledgment with failure
                        ackRequest.sendAckData(false);
                    }
                } catch (NullPointerException e) {
                    // Log the exception for debugging purposes
                    System.err.println("NullPointerException occurred: " + e.getMessage());
                    e.printStackTrace();
                    // Optionally, send an error response
                    ackRequest.sendAckData(false, "Login failed due to internal error.");
                } catch (Exception e) {
                    // Log the exception for debugging purposes
                    System.err.println("Exception occurred: " + e.getMessage());
                    e.printStackTrace();
                    // Optionally, send an error response
                    ackRequest.sendAckData(false, "An error occurred during login.");
                }
            }
        });
        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
                try {
                    List<ModelUserAccount> list = serviceUser.getUser(userID);
                    sioc.sendEvent("list_user", list.toArray());
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        });
        server.start();
        textArea.append("Server has Start on port : " + PORT_NUMBER + "\n");
    }
}
