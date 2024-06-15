package com.component;

import com.model.ModelFileSender;
import com.model.ModelReceiveImage;
import java.awt.Color;
import java.io.IOException;
import java.time.LocalTime;
import javax.swing.Icon;

public class ChatLeftWithProfile extends javax.swing.JLayeredPane {
    private LocalTime time;
    public ChatLeftWithProfile() {
        initComponents();
        txt.setBackground(new Color(242, 242, 242));
    }

    public void setUserProfile(String user) {
        txt.setUserProfile(user);
    }

    public void setImageProfile(Icon image) {
        IaImage.setImage(image);
    }

    public void setText(String text) {
        if (text.equals("")) {
            txt.hideText();
        } else {
            txt.setText(text);
        }

    }

    public void setImage(Icon... image) {
   //     txt.setImage(false, image);
    }
    
    public void setImage(ModelReceiveImage dataImage) throws IOException {
        txt.setImage(false, dataImage);
    }
    
    public void setFile(ModelFileSender file){
        txt.setFile(true, file);
    }

    public void setTime(){
        time = LocalTime.now();
        if (time != null) {
            String formattedTime = String.format("%02d:%02d", time.getHour(), time.getMinute());
            txt.setTime(formattedTime);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        IaImage = new com.swing.ImageAvatar();
        txt = new com.component.ChatItem();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        IaImage.setBorderSize(0);
        IaImage.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/icon/testing/frieren.jpg"))); // NOI18N
        IaImage.setMaximumSize(new java.awt.Dimension(31, 31));
        IaImage.setMinimumSize(new java.awt.Dimension(31, 31));
        IaImage.setPreferredSize(new java.awt.Dimension(31, 31));

        jLayeredPane1.setLayer(IaImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addComponent(IaImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
                jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(IaImage, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jLayeredPane1);
        add(txt);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.ImageAvatar IaImage;
    private javax.swing.JLayeredPane jLayeredPane1;
    private com.component.ChatItem txt;
    // End of variables declaration//GEN-END:variables
}
