package com.view.form;

import com.controller.event.PublicEvent;
import com.model.ModelLogin;

public class PLogin extends javax.swing.JPanel {

    public PLogin() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        cmdLogin = new javax.swing.JButton();
        cmdRegister = new javax.swing.JButton();

        lbTitle.setFont(new java.awt.Font("Arial", 0, 30)); 
        lbTitle.setForeground(new java.awt.Color(100, 100, 100));
        lbTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTitle.setText("Login");

        jLabel1.setText("Username");

        jLabel2.setText("Password");

        cmdLogin.setFont(new java.awt.Font("Arial", 0, 12)); 
        cmdLogin.setText("Login");
        cmdLogin.addActionListener((java.awt.event.ActionEvent evt) -> {
            cmdLoginActionPerformed(evt);
        });

        cmdRegister.setFont(new java.awt.Font("Arial", 0, 11)); 
        cmdRegister.setForeground(new java.awt.Color(0, 153, 51));
        cmdRegister.setText("Register");
        cmdRegister.setContentAreaFilled(false);
        cmdRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmdRegister.addActionListener((java.awt.event.ActionEvent evt) -> {
            cmdRegisterActionPerformed(evt);
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUser)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPass)
                    .addComponent(cmdLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                    .addComponent(cmdRegister, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(cmdLogin)
                .addGap(18, 18, 18)
                .addComponent(cmdRegister)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    private void cmdLoginActionPerformed(java.awt.event.ActionEvent evt) {
        PublicEvent.getInstance().getEventLogin().login(new ModelLogin(txtUser.getText(), String.valueOf(txtPass.getPassword())));
    }

    private void cmdRegisterActionPerformed(java.awt.event.ActionEvent evt) {
        PublicEvent.getInstance().getEventLogin().goRegister();
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton cmdLogin;
    private javax.swing.JButton cmdRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration                   
}
