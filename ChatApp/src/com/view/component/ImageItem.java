package com.view.component;

import com.model.ModelFileSender;
import com.model.ModelReceiveFile;
import com.model.ModelReceiveImage;
import com.controller.service.ServiceController;
import com.view.swing.blurHash.BlurHash;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import com.controller.event.EventFileReceiverController;
import com.controller.event.EventFileSenderController;

public class ImageItem extends javax.swing.JLayeredPane {

    public ImageItem() {
        initComponents();
    }

    public void setImage(Icon image, ModelFileSender fileSender) {
        fileSender.addEvent(new EventFileSenderController() {
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
        try {
            ServiceController.getInstance().addFileReceiver(dataImage.getFileID(), new EventFileReceiverController() {
                @Override
                public void onReceiving(double percentage) {
                    progress.setValue((int) percentage);
                }

                @Override
                public void onStartReceiving() {

                }

                @Override
                public void onFinish(File file) {
                    progress.setVisible(false);
                    pic.setImage(new ImageIcon(file.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setFile(ModelReceiveFile dataFile) {
        try {
            ServiceController.getInstance().addFileReceiver(dataFile.getFileID(), new EventFileReceiverController() {
                @Override
                public void onReceiving(double percentage) {
                    progress.setValue((int) percentage);
                }

                @Override
                public void onStartReceiving() {

                }

                @Override
                public void onFinish(File file) {
                    progress.setVisible(false);
                    pic.setImage(new ImageIcon(file.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new com.view.swingController.PictureBox();
        progress = new com.view.swingController.Progress();

        progress.setBackground(new java.awt.Color(255, 255, 255));
        progress.setProgressType(com.view.swingController.Progress.ProgressType.CANCEL);
        pic.add(progress);
        progress.setBounds(50, 40, 60, 60);

        setLayer(pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.view.swingController.PictureBox pic;
    private com.view.swingController.Progress progress;
    // End of variables declaration//GEN-END:variables
}