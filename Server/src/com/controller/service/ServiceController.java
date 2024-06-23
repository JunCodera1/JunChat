    package com.controller.service;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.model.app.MessageType;
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

public class ServiceController {
    
    private static ServiceController instance;
    private SocketIOServer server;
    private final ServiceUserController serviceUser;
    private final ServiceFileController serviceFile;
    private final List<ModelClient> listClient;
    private final JTextArea textArea;
    private final int PORT_NUMBER = 138;
    
    public static ServiceController getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new ServiceController(textArea);
        }
        return instance;
    }
    
    private ServiceController(JTextArea textArea) {
        this.textArea = textArea;
        serviceUser = new ServiceUserController();
        serviceFile = new ServiceFileController();
//        ServiceChatHistoryController serviceChatHistoryController = new ServiceChatHistoryController(); 
        listClient = new ArrayList<>();
    }
    
    public void startServer() {
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener((SocketIOClient sioc) -> {
            textArea.append("One client connected\n");
        });
        server.addEventListener("register", ModelRegister.class, (SocketIOClient sioc, ModelRegister t, AckRequest ar) -> {
            ModelMessage message = serviceUser.register(t);
            ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
            if (message.isAction()) {
                textArea.append("User has Register :" + t.getUserName() + " Pass :" + t.getPassword() + "\n");
                server.getBroadcastOperations().sendEvent("list_user", (ModelUserAccount) message.getData());
                addClient(sioc, (ModelUserAccount) message.getData());
            }
        });
        server.addEventListener("login", ModelLogin.class, (SocketIOClient sioc, ModelLogin t, AckRequest ar) -> {
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
        });
        server.addEventListener("list_user", Integer.class, (SocketIOClient sioc, Integer userID, AckRequest ar) -> {
            try {
                List<ModelUserAccount> list = serviceUser.getUser(userID);
                sioc.sendEvent("list_user", list.toArray());
            } catch (SQLException e) {
                System.err.println(e);
            }
        });
        server.addEventListener("get_chat_history", ModelUserAccount.class, (SocketIOClient sioc, ModelUserAccount t, AckRequest ar) -> {
        });
        server.addEventListener("send_to_user", ModelSendMessage.class, (SocketIOClient sioc, ModelSendMessage t, AckRequest ar) -> {
            sendToClient(t, ar);
        });
        server.addEventListener("send_file", ModelPackageSender.class, (SocketIOClient sioc, ModelPackageSender t, AckRequest ar) -> {
            try {
                System.out.println("Receiving file data...");
                serviceFile.receiveFile(t);
                if (t.isFinish()) {
                    System.out.println("File receiving finished. Closing file...");
                    ar.sendAckData(true);
                    ModelReceiveImage dataImage = new ModelReceiveImage();
                    dataImage.setFileID(t.getFileID());
                    ModelSendMessage message = serviceFile.closeFile(dataImage);
                    System.out.println("Sending temporary file to client...");
                    sendTempFileToClient(message, dataImage);
                } else {
                    ar.sendAckData(true);
                }
            } catch (IOException | SQLException e) {
                ar.sendAckData(false);
                e.printStackTrace();
            }
        });
        server.addEventListener("get_file", Integer.class, (SocketIOClient sioc, Integer t, AckRequest ar) -> {
            ModelFile file = serviceFile.initFile(t);
            long fileSize = serviceFile.getFileSize(t);
            ar.sendAckData(file.getFileExtension(), fileSize);
        });
        server.addEventListener("request_file", ModelRequestFile.class, (SocketIOClient sioc, ModelRequestFile t, AckRequest ar) -> {
            byte[] data = serviceFile.getFileData(t.getCurrentLength(), t.getFileID());
            if (data != null) {
                ar.sendAckData(data);
            } else {
                ar.sendAckData();
            }
        });
        server.addDisconnectListener((SocketIOClient sioc) -> {
            textArea.append("One client disconnected\n");
            int userID = removeClient(sioc);
            if (userID != 0) {
                ModelUserAccount user = getUserByID(userID);
                if (user != null) {
                    textArea.append(user.getUserName() + " client disconnected\n");
                }
                userDisconnect(userID);
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
