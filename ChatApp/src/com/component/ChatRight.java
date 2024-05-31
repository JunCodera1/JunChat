package com.component;

import com.model.ModelFileSender;
import com.model.ModelReceiveImage;
import java.awt.Color;
import java.time.LocalTime;
import javax.swing.Icon;

public class ChatRight extends javax.swing.JLayeredPane {
    
    private LocalTime time;

    public ChatRight() {
        initComponents();
        txt.setBackground(new Color(57, 239, 154));
    }

    public void setText(String text) {
        if (text.equals("")) {
            txt.hideText();
        } else {
            txt.setText(text);
        }
        txt.seen();
    }

    public void setImage(ModelFileSender fileSender) {
        txt.setImage(true, fileSender);
    }
    
    public void setImage(String... image) {
        //txt.setImage(true, image);
    }
    
    public void setFile(String fileName, String fileSize){
        txt.setFile(fileName, fileSize);
    }
    
    public void setEmoji(Icon icon){
        txt.hideText();
        txt.setEmoji(true, icon);
    }

    public void setTime() {
        time = LocalTime.now();
        if (time != null) {
            String formattedTime = String.format("%02d:%02d", time.getHour(), time.getMinute());
            txt.setTime(formattedTime); // Call the method to set the time in txt
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt = new com.component.ChatItem();

        setLayer(txt, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.component.ChatItem txt;
    // End of variables declaration//GEN-END:variables
}
