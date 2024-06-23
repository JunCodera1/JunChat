package com.view.component;

import com.model.app.MessageType;
import com.view.emoji.Emoji;
import com.view.emoji.ModelEmoji;
import com.controller.event.PublicEvent;
import com.main.Main;
import com.model.ModelSendMessage;
import com.model.ModelUserAccount;
import com.controller.service.ServiceController;
import com.view.swingController.ScrollBar;
import com.view.swingController.WrapLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import net.miginfocom.swing.MigLayout;

public class PanelMore extends javax.swing.JPanel {

    public ModelUserAccount getUser() {
        return user;
    }

    public void setUser(ModelUserAccount user) {
        this.user = user;
    }

    private ModelUserAccount user;

    public PanelMore() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx"));
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.LINE_AXIS));
        panelHeader.add(getButtonImage());
        panelHeader.add(getButtonFile());
        panelHeader.add(getButtonSoundRecord());
        panelHeader.add(getEmojiStyle());

        add(panelHeader, "w 100%, h 30!, wrap");
        panelDetail = new JPanel();
        panelDetail.setLayout(new WrapLayout(WrapLayout.LEFT));
        JScrollPane ch = new JScrollPane(panelDetail);
        ch.setBorder(null);
        ch.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        ch.setVerticalScrollBar(new ScrollBar());
        add(ch, "w 100%, h 100%");
    }

    private JButton getButtonImage() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/icon/image.png")));
        cmd.addActionListener((ActionEvent e) -> {
            JFileChooser ch = new JFileChooser();
            ch.setMultiSelectionEnabled(true);
            ch.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || isImageFile(file);
                }

                @Override
                public String getDescription() {
                    return "Image Files";
                }
            });
            int option = ch.showOpenDialog(Main.getFrames()[0]);
            if (option == JFileChooser.APPROVE_OPTION) {
                File[] files = ch.getSelectedFiles();
                try {
                    for (File file : files) {
                        ModelSendMessage message = new ModelSendMessage(MessageType.IMAGE, ServiceController.getInstance().getUser().getUserID(), user.getUserID(), "");
                        ServiceController.getInstance().addFile(file, message);
                        PublicEvent.getInstance().getEventChat().sendMessage(message);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return cmd;
    }

    private JButton getButtonFile() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/icon/link.png")));
        cmd.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return file.isDirectory() || isFile(file);
                }

                @Override
                public String getDescription() {
                    return "All Files";
                }
            });

            int option = fileChooser.showOpenDialog(Main.getFrames()[0]);
            if (option == JFileChooser.APPROVE_OPTION) {
                File[] files = fileChooser.getSelectedFiles();
                try {
                    for (File file : files) {
                        ModelSendMessage message = new ModelSendMessage(MessageType.FILE, ServiceController.getInstance().getUser().getUserID(), user.getUserID(), "");
                        ServiceController.getInstance().addFile(file, message);
                        PublicEvent.getInstance().getEventChat().sendMessage(message);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        return cmd;
    }



    
    private JButton getButtonSoundRecord() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/icon/record.png")));
        cmd.addActionListener((ActionEvent e) -> {
            //Update next
        });
        return cmd;
    }

    private JButton getEmojiStyle() {
        OptionButton cmd = new OptionButton();
        cmd.setIcon(Emoji.getInstance().getEmoji(1).toSize(25, 25).getIcon());
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearSelected();
                cmd.setSelected(true);
                panelDetail.removeAll();

                for (ModelEmoji d : Emoji.getInstance().getStyle()) {
                    panelDetail.add(getButton(d));
                }

                panelDetail.repaint();
                panelDetail.revalidate();
            }
        });
        return cmd;
    }



    private JButton getButton(ModelEmoji data) {
        JButton cmd = new JButton(data.getIcon());
        cmd.setName(data.getId() + "");
        cmd.setBorder(new EmptyBorder(3, 3, 3, 3));
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setContentAreaFilled(false);
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ModelSendMessage message = new ModelSendMessage(MessageType.EMOJI, ServiceController.getInstance().getUser().getUserID(), user.getUserID(), data.getId() + "");
                    sendMessage(message);
                    PublicEvent.getInstance().getEventChat().sendMessage(message);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(PanelMore.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return cmd;
    }

    private void sendMessage(ModelSendMessage data) throws UnknownHostException {
        ServiceController.getInstance().getClient().emit("send_to_user", data.toJsonObject());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 504, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 65, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearSelected() {
        for (Component c : panelHeader.getComponents()) {
            if (c instanceof OptionButton) {
                ((OptionButton) c).setSelected(false);
            }
        }
    }

  
   private static final String[] IMAGE_EXTENSIONS = {".jpg", ".png", ".jpeg", ".gif"};


   private static final String[] TEXT_EXTENSIONS = {".txt", ".cpp", ".c", ".html", ".docx", ".java", ".py", ".xml", ".json"};

   
   private boolean isImageFile(File file) {
       return hasExtension(file, IMAGE_EXTENSIONS);
   }

  
   private boolean isFile(File file) {
        return hasExtension(file, TEXT_EXTENSIONS);
    }

    private boolean hasExtension(File file, String[] extensions) {
        String name = file.getName().toLowerCase();
        for (String ext : extensions) {
            if (name.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    private JPanel panelHeader;
    private JPanel panelDetail;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
