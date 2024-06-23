package com.view.form;

import com.view.component.ChatBody;
import com.view.component.ChatBottom;
import com.view.component.ChatTitle;
import com.controller.event.PublicEvent;
import com.model.ModelReceiveMessage;
import com.model.ModelSendMessage;
import com.model.ModelUserAccount;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.miginfocom.swing.MigLayout;
import com.controller.event.EventChatController;

public class Chat extends javax.swing.JPanel {

    private ChatTitle chatTitle;
    private ChatBody chatBody;
    private ChatBottom chatBottom;

    public Chat() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, fill]0[shrink 0]0"));
        chatTitle = new ChatTitle();
        chatBody = new ChatBody();
        chatBottom = new ChatBottom();
        PublicEvent.getInstance().addEventChat(new EventChatController() {
            @Override
            public void sendMessage(ModelSendMessage data) {
                chatBody.addItemRight(data);
            }

            @Override
            public void receiveMessage(ModelReceiveMessage data) {
                if (chatTitle.getUser().getUserID() == data.getFromUserID()) {
                    try {
                        chatBody.addItemLeft(data);
                    } catch (IOException ex) {
                        Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::30%");
    }

    public void setUser(ModelUserAccount user) {
        chatTitle.setUsername(user);
        chatBottom.setUser(user);
        chatBody.clearChat();
    }

    public void updateUser(ModelUserAccount user) {
        chatTitle.updateUser(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 726, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 624, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
