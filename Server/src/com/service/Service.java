package com.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.app.MessageType;
import com.model.ModelClient;
import com.model.ModelFile;
import com.model.ModelLogin;
import com.model.ModelMessage;
import com.model.ModelPackageSender;
import com.model.ModelReceiveImage;
import com.model.ModelReceiveMessage;
import com.model.ModelRegister;
import com.model.ModelRequestFile;
import com.model.ModelSendMessage;
import com.model.ModelUserAccount;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;

public class Service {
    
    private static Service instance;
    private SocketIOServer server;
    private ServiceUser serviceUser;
    private ServiceFile serviceFile;
    private List<ModelClient> listClient;
    private JTextArea textArea;
    private final int PORT_NUMBER = 138;
    
    public static Service getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }
    
    private Service(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUser();
        serviceFile = new ServiceFile();
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
            public void onData(SocketIOClient sioc, ModelLogin t, AckRequest ar) throws Exception {
                ModelUserAccount login = serviceUser.login(t);
                if (login != null) {
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getUserID());
                    textArea.append(t.getUserName() + " connected\n");
                    
                } else {
                    textArea.append(t.getUserName() + "client disconnected\n");
                    ar.sendAckData(false);
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
        server.addEventListener("send_to_user", ModelSendMessage.class, new DataListener<ModelSendMessage>() {
            @Override
            public void onData(SocketIOClient sioc, ModelSendMessage t, AckRequest ar) throws Exception {
                sendToClient(t, ar);
            }
        });
        server.addEventListener("send_file", ModelPackageSender.class, new DataListener<ModelPackageSender>() {
            @Override
            public void onData(SocketIOClient sioc, ModelPackageSender t, AckRequest ar) throws Exception {
                try {
                    serviceFile.receiveFile(t);
                    if (t.isFinish()) {
                        ar.sendAckData(true);
                        ModelReceiveImage dataImage = new ModelReceiveImage();
                        dataImage.setFileID(t.getFileID());
                        ModelSendMessage message = serviceFile.closeFile(dataImage);
                        //  Send to client 'message'
                        sendTempFileToClient(message, dataImage);
                        
                    } else {
                        ar.sendAckData(true);
                    }
                } catch (IOException | SQLException e) {
                    ar.sendAckData(false);
                    e.printStackTrace();
                }
            }
        });
        server.addEventListener("get_file", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                ModelFile file = serviceFile.initFile(t);
                long fileSize = serviceFile.getFileSize(t);
                ar.sendAckData(file.getFileExtension(), fileSize);
            }
        });
        server.addEventListener("request_file", ModelRequestFile.class, new DataListener<ModelRequestFile>() {
            @Override
            public void onData(SocketIOClient sioc, ModelRequestFile t, AckRequest ar) throws Exception {
                byte[] data = serviceFile.getFileData(t.getCurrentLength(), t.getFileID());
                if (data != null) {
                    ar.sendAckData(data);
                } else {
                    ar.sendAckData();
                }
            }
        });
        server.addDisconnectListener(new DisconnectListener() {
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if (userID != 0) {
                   ModelUserAccount user = getUserByID(userID);
                    if (user != null) {
                        // Thêm thông báo người dùng ngắt kết nối vào textArea
                        textArea.append(user.getUserName() + " client disconnected\n");
                    }
                    userDisconnect(userID);
                }
            }
        });
        server.start();
        textArea.append("Server has Start on port : " + PORT_NUMBER + "\n");
    }
    
    private void userConnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }
    
    private void userDisconnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
    
    private void addClient(SocketIOClient client, ModelUserAccount user) {
        listClient.add(new ModelClient(client, user));
    }
    
    private void sendToClient(ModelSendMessage data, AckRequest ar) {
        if (data.getMessageType() == MessageType.IMAGE.getValue() || data.getMessageType() == MessageType.FILE.getValue()) {
            try {
                ModelFile file = serviceFile.addFileReceiver(data.getText());
                serviceFile.initFile(file, data);
                ar.sendAckData(file.getFileID());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            for (ModelClient c : listClient) {
                if (c.getUser().getUserID() == data.getToUserID()) {
                    c.getClient().sendEvent("receive_ms", new ModelReceiveMessage(data.getMessageType(), data.getFromUserID(), data.getText(), null));
                    break;
                }
            }
        }
    }
    
    private void sendTempFileToClient(ModelSendMessage data, ModelReceiveImage dataImage) {
        for (ModelClient c : listClient) {
            if (c.getUser().getUserID() == data.getToUserID()) {
                c.getClient().sendEvent("receive_ms", new ModelReceiveMessage(data.getMessageType(), data.getFromUserID(), data.getText(), dataImage));
                break;
            }
        }
    }
    
    public int removeClient(SocketIOClient client) {
        for (ModelClient d : listClient) {
            if (d.getClient() == client) {
                listClient.remove(d);
                return d.getUser().getUserID();
            }
        }
        return 0;
    }
    
    public List<ModelClient> getListClient() {
        return listClient;
    }
    
    private ModelUserAccount getUserByID(int userID) {
        for (ModelClient c : listClient) {
            if (c.getUser().getUserID() == userID) {
                return c.getUser();
            }
        }
        return null;
    }
}
