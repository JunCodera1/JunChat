package com.controller.service;

import com.controller.event.PublicEvent;
import com.model.ModelFileReceiver;
import com.model.ModelFileSender;
import com.model.ModelReceiveMessage;
import com.model.ModelSendMessage;
import com.model.ModelUserAccount;
import io.socket.client.IO;
import io.socket.client.Socket;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import com.controller.event.EventFileReceiverController;

public class ServiceController {

    private static ServiceController instance;
    private Socket client;
    private final int PORT_NUMBER = 138;
    private final String IP = "localhost";
    private ModelUserAccount user;
    private final List<ModelFileSender> fileSender;
    private final List<ModelFileReceiver> fileReceiver;

    public static ServiceController getInstance() {
        if (instance == null) {
            instance = new ServiceController();
        }
        return instance;
    }

    private ServiceController() {
        fileSender = new ArrayList<>();
        fileReceiver = new ArrayList<>();
    }

    public void startServer() {
        try {
            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
            client.on("list_user", (Object... os) -> {
                //  list user
                List<ModelUserAccount> users = new ArrayList<>();
                for (Object o : os) {
                    ModelUserAccount u = new ModelUserAccount(o);
                    if (u.getUserID() != user.getUserID()) {
                        users.add(u);
                    }
                }
                PublicEvent.getInstance().getEventMenuLeft().newUser(users);
            });
            client.on("user_status", (Object... os) -> {
                int userID = (Integer) os[0];
                boolean status = (Boolean) os[1];
                if (status) {
                    //  connect
                    PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                } else {
                    //  disconnect
                    PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                }
            });
            client.on("receive_ms", (Object... os) -> {
                ModelReceiveMessage message = new ModelReceiveMessage(os[0]);
                PublicEvent.getInstance().getEventChat().receiveMessage(message);
            });
            client.open();
        } catch (URISyntaxException e) {
            error(e);
        }
    }

    public ModelFileSender addFile(File file, ModelSendMessage message) throws IOException {
        ModelFileSender data = new ModelFileSender(file, client, message);
        message.setFile(data);
        fileSender.add(data);
        //  For send file one by one
        if (fileSender.size() == 1) {
            data.initSend();
        }
        return data;
    }

    public void fileSendFinish(ModelFileSender data) throws IOException {
        fileSender.remove(data);
        if (!fileSender.isEmpty()) {
            //  Start send new file when old file sending finish
            fileSender.get(0).initSend();
        }
    }

    public void fileReceiveFinish(ModelFileReceiver data) throws IOException {
        fileReceiver.remove(data);
        if (!fileReceiver.isEmpty()) {
            fileReceiver.get(0).initReceive();
        }
    }

    public void addFileReceiver(int fileID, EventFileReceiverController event) throws IOException {
        ModelFileReceiver data = new ModelFileReceiver(fileID, client, event);
        fileReceiver.add(data);
        if (fileReceiver.size() == 1) {
            data.initReceive();
        }
    }

    public Socket getClient() {
        return client;
    }

    public ModelUserAccount getUser() {
        return user;
    }

    public void setUser(ModelUserAccount user) {
        this.user = user;
    }

    private void error(Exception e) {
        System.err.println(e);
    }
}
