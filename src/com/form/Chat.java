package com.form;

import com.component.ChatBody;
import com.component.ChatBottom;
import com.component.ChatTitle;
import net.miginfocom.swing.MigLayout;

public class Chat extends javax.swing.JPanel {

    public Chat() {
        initComponents();
        init();
    }
    
    private void init(){
        setLayout(new MigLayout("fillx", "0[fill]0", "[]0[100%, bottom]0[shrink 0]"));
        ChatTitle chatTitle = new ChatTitle();
        ChatBody chatBody = new ChatBody();
        ChatBottom chatBottom = new ChatBottom();
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom);
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
