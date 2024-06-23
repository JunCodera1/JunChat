package com.view.component;

import com.controller.event.PublicEvent;
import com.model.ModelUserAccount;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemPeople extends javax.swing.JPanel {

    public ModelUserAccount getUser() {
        return user;
    }
    private boolean mouseOver;
    private final ModelUserAccount user;

    public ItemPeople(ModelUserAccount user) {
        this.user = user;
        initComponents();
        lb.setText(user.getUserName());
        activeStatus1.setActive(user.isStatus());
        init();
    }

    public void updateStatus() {
        activeStatus1.setActive(user.isStatus());
    }

    private void init() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                setBackground(new Color(230, 230, 230));
                mouseOver = true;
            }

            @Override
            public void mouseExited(MouseEvent me) {
                setBackground(new Color(242, 242, 242));
                mouseOver = false;
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                if (mouseOver) {
                    PublicEvent.getInstance().getEventMain().selectUser(user);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imageAvatar1 = new com.view.swingController.ImageAvatar();
        lb = new javax.swing.JLabel();
        lbStatus = new javax.swing.JLabel();
        activeStatus1 = new com.view.swingController.ActiveStatus();

        setBackground(new java.awt.Color(244, 244, 244));
        setPreferredSize(new java.awt.Dimension(200, 50));

        imageAvatar1.setBorderSize(0);
        imageAvatar1.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/icon/profile.png"))); // NOI18N

        lb.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lb.setText("Name");

        lbStatus.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        lbStatus.setForeground(new java.awt.Color(117, 117, 117));
        lbStatus.setText("Name");

        activeStatus1.setActive(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(activeStatus1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(106, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageAvatar1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lb)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbStatus, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(activeStatus1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.view.swingController.ActiveStatus activeStatus1;
    private com.view.swingController.ImageAvatar imageAvatar1;
    private javax.swing.JLabel lb;
    private javax.swing.JLabel lbStatus;
    // End of variables declaration//GEN-END:variables
}
