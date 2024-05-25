package com.service;


import io.socket.client.Socket;
import io.socket.client.IO;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextArea;

public class Service {

    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
    }

    public void startServer() {
        try {
            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
        } catch (URISyntaxException e) {
            error(e);
        }
        client.open();
    }
    
    public Socket getClient() {
        return client;
    }
    
    private void error(Exception e){
        System.err.println(e);
    }
}
