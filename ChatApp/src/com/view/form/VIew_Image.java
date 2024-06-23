package com.view.form;

import com.controller.event.PublicEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.SwingUtilities;

public class VIew_Image extends javax.swing.JComponent {

    public VIew_Image() {
        initComponents();
    }

    private Icon image;

    public void viewImage(Icon image) {
        this.image = image;
        pictureBox1.setImage(image);
        setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pictureBox1 = new com.view.swingController.PictureBox();
        jButton1 = new javax.swing.JButton();

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/save.png"))); // NOI18N
        pictureBox1.add(jButton1);
        jButton1.setBounds(310, 220, 70, 71);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pictureBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void picMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_picMousePressed
        if (SwingUtilities.isLeftMouseButton(evt)) {
            setVisible(false);
        }
    }//GEN-LAST:event_picMousePressed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        PublicEvent.getInstance().getEventImageView().saveImage(image);
    }//GEN-LAST:event_cmdSaveActionPerformed

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(grphcs);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private com.view.swingController.PictureBox pictureBox1;
    // End of variables declaration//GEN-END:variables
}
