package com.view.component;

import com.model.app.MessageType;
import com.view.emoji.Emoji;
import com.model.ModelFileSender;
import com.model.ModelReceiveMessage;
import com.model.ModelSendMessage;
import com.view.swingController.ScrollBar;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.IOException;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

public class ChatBody extends javax.swing.JPanel {

    public ChatBody() {
        initComponents();
        init();
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(ModelReceiveMessage data) throws IOException {
        if (data.getMessageType() == MessageType.TEXT) {
            ChatLeft item = new ChatLeft();
            item.setText(data.getText());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            ChatLeft item = new ChatLeft();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if (data.getMessageType() == MessageType.IMAGE) {
            ChatLeft item = new ChatLeft();
            item.setText("");
            item.setImage(data.getDataImage());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        }
        else if(data.getMessageType() == MessageType.FILE){
            ChatLeft item = new ChatLeft();
            item.setText("");
            item.setFile(data.getDataFile());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        }
        repaint();
        revalidate();
    }

    public void addItemFile(String text, String user, ModelFileSender file) {
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setFile(file);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(ModelSendMessage data) {
        if (data.getMessageType() == MessageType.TEXT) {
            ChatRight item = new ChatRight();
            item.setText(data.getText());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            ChatRight item = new ChatRight();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        } else if (data.getMessageType() == MessageType.IMAGE) {
            ChatRight item = new ChatRight();
            item.setText("");
            item.setImage(data.getFile());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        } else if (data.getMessageType() == MessageType.FILE){
            ChatRight item = new ChatRight();
            item.setText("");
            item.setFile(data.getFile());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        }
        repaint();
        revalidate();
        scrollToBottom();
    }

    public void addItemFileRight(ModelFileSender file) {
        ChatRight item = new ChatRight();
        item.setText("");
        item.setFile(file);
        body.add(item, "wrap, al right, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addDate(String date) {
        ChatDate item = new ChatDate();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }

    public void clearChat() {
        body.removeAll();
        repaint();
        revalidate();
    }

    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 828, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 556, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
