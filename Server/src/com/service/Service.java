package com.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.model.ModelClient;
import com.model.ModelLogin;
import com.model.ModelMessage;
import com.model.ModelRegister;
import com.model.ModelUserAccount;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

public class Service {

    private static Service instance;
    private SocketIOServer server;
    private ServiceUser serviceUser;
    private List<ModelClient> listClient;
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
        listClient = new ArrayList<>();
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
                    addClient(sioc, (ModelUserAccount) message.getData());
                }
            }
        });
        server.addEventListener("login", ModelLogin.class, new DataListener<ModelLogin>() {
            @Override
            public void onData(SocketIOClient sioc, ModelLogin loginData, AckRequest ackRequest) throws Exception {
                try {
                    ModelUserAccount userAccount = serviceUser.login(loginData);

                    if (userAccount != null) {
                        int userID = userAccount.getUserID();
                        ackRequest.sendAckData(true, userAccount);
                        addClient(sioc, userAccount);
                        userConnect(userAccount.getUserID());
                    } else {
                        ackRequest.sendAckData(false);
                    }
                } catch (NullPointerException e) {
                    System.err.println("NullPointerException occurred: " + e.getMessage());
                    e.printStackTrace();
                    ackRequest.sendAckData(false, "Login failed due to internal error.");
                } catch (Exception e) {
                    System.err.println("Exception occurred: " + e.getMessage());
                    e.printStackTrace();
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
        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if(userID != 0){
                    // removed
                    userDisconnect(userID);
                }
            }
        });
        server.start();
        textArea.append("Server has Start on port : " + PORT_NUMBER + "\n");
    }
    
    private void userConnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }
    
    private void userDisconnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
    
    private void addClient(SocketIOClient client, ModelUserAccount user){
        listClient.add(new ModelClient(client, user));
    }
    
    public int removeClient(SocketIOClient client){
        for(ModelClient d : listClient){
            if(d.getClient() == client){
                listClient.remove(d);
                return d.getUser().getUserID();
            }
        }
        return 0;
    }
    public List<ModelClient> getListClient() {
        return listClient;
    }
}
