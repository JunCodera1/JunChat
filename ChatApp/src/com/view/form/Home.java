package com.view.form;

import com.model.ModelUserAccount;
import net.miginfocom.swing.MigLayout;

public class Home extends javax.swing.JLayeredPane {

    private Chat chat;

    public Home() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[200!]5[fill, 100%]5[200!]0", "0[fill]0"));
        this.add(new MenuLeft());
        chat = new Chat();
        this.add(chat);
        this.add(new MenuRight());
        chat.setVisible(false);
    }

    public void setUser(ModelUserAccount user) {
        chat.setUser(user);
        chat.setVisible(true);
    }

    public void updateUser(ModelUserAccount user) {
        chat.updateUser(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1067, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 589, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
