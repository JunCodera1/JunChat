package com.component;

import java.awt.Color;
import java.time.LocalTime;
import javax.swing.Icon;

public class ChatLeft extends javax.swing.JLayeredPane {
    private LocalTime time;
    public ChatLeft() {
        initComponents();
        txt.setBackground(new Color(242, 242, 242));
    }

    public void setText(String text) {
        if (text.equals("")) {
            txt.hideText();
        } else {
            txt.setText(text);
        }

    }

    public void setImage(Icon... image) {
//        txt.setImage(false, image);
    }
    
    public void setImage(String... image) {
        txt.setImage(false, image);
    }

    public void setFile(String fileName, String fileSize){
        txt.setFile(fileName, fileSize);
    }
    
    public void setEmoji(Icon icon){
        txt.hideText();
        txt.setEmoji(false, icon);
    }
    
    public void setTime() {
        time = LocalTime.now();
        if (time != null) {
            String formattedTime = String.format("%02d:%02d", time.getHour(), time.getMinute());
            txt.setTime(formattedTime);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new com.component.ChatItem();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.component.ChatItem txt;
    // End of variables declaration//GEN-END:variables

}
