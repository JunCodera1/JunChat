package com.component;

import com.event.EventFileReceiver;
import com.event.EventFileSender;
import com.model.ModelFileSender;
import com.model.ModelReceiveImage;
import com.service.Service;
import com.swing.blurHash.BlurHash;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
    if (dataImage == null) {
        System.err.println("dataImage bị null");
        return;
    }

    int width = dataImage.getWidth();
    int height = dataImage.getHeight();
    String blurHash = dataImage.getImage();

    try {
        // Giải mã ảnh từ BlurHash
        int[] data = BlurHash.decode(blurHash, width, height, 1);

        // Kiểm tra dữ liệu giải mã
        if (data == null || data.length != width * height) {
            System.err.println("Lỗi giải mã dữ liệu từ BlurHash. Kết quả giải mã bị null hoặc kích thước không khớp.");
            return;
        }

        // In ra một vài giá trị của data để kiểm tra
        System.out.println("Dữ liệu giải mã: " + Arrays.toString(Arrays.copyOf(data, Math.min(data.length, 10))));

        // Tạo BufferedImage từ dữ liệu giải mã
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, data, 0, width);

        // Tạo Icon từ BufferedImage
        Icon icon = new ImageIcon(img);

        // Cập nhật hình ảnh trong luồng UI
        SwingUtilities.invokeLater(() -> {
            pic.setImage(icon);
            System.out.println("Hình ảnh ban đầu đã được cập nhật");
        });

        // Thiết lập nhận file
        Service.getInstance().addFileReceiver(dataImage.getFileID(), new EventFileReceiver() {
            @Override
            public void onReceiving(double percentage) {
                SwingUtilities.invokeLater(() -> progress.setValue((int) percentage));
            }

            @Override
            public void onStartReceiving() {
                // Có thể thêm xử lý khi bắt đầu nhận nếu cần
            }

            @Override
            public void onFinish(File file) {
                SwingUtilities.invokeLater(() -> {
                    progress.setVisible(false);
                    pic.setImage(new ImageIcon(file.getAbsolutePath()));
                    System.out.println("Hình ảnh đã được cập nhật từ file");
                });
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Lỗi khi giải mã hoặc nhận file: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
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
