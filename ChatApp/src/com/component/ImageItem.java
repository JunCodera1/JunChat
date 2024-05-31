package com.component;

import com.event.EventFileSender;
import com.model.ModelFileSender;
import com.model.ModelReceiveImage;
import com.swing.blurHash.BlurHash;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageItem extends javax.swing.JLayeredPane {

    public ImageItem() {
        initComponents();
    }

    public void setImage(Icon image, ModelFileSender fileSender) {
        fileSender.addEvent(new EventFileSender() {
            @Override
            public void onSending(double percentage) {
                progress.setValue((int) percentage);
            }

            @Override
            public void onStartSending() {
            }

            @Override
            public void onFinish() {
                progress.setVisible(false);
            }
        });
        pic.setImage(image);
    }

    public void setImage(ModelReceiveImage dataImage) {
        int width = dataImage.getWidth();
        int height = dataImage.getHeight();
        int[] data = BlurHash.decode(dataImage.getImage(), width, height, 1);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, data, 0, width);
        Icon icon = new ImageIcon(img);
        pic.setImage(icon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new com.swing.PictureBox();
        progress = new com.swing.Progress();

        progress.setForeground(new java.awt.Color(255, 255, 255));
        progress.setProgressType(com.swing.Progress.ProgressType.CANCEL);

        pic.setLayer(progress, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
                picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(picLayout.createSequentialGroup()
                                .addContainerGap(35, Short.MAX_VALUE)
                                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(36, Short.MAX_VALUE))
        );
        picLayout.setVerticalGroup(
                picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(picLayout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(progress, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(41, Short.MAX_VALUE))
        );

        setLayer(pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.PictureBox pic;
    private com.swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
