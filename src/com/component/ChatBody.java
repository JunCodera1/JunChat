package com.component;

import com.swing.ScrollBar;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;

public class ChatBody extends javax.swing.JPanel {

    public ChatBody() {
        initComponents();
        init();
          //addItemRight("Send a text message to a group of contacts. Include photos, personalize your texts, and track who clicked your links.", new ImageIcon(getClass().getResource("/com/icon/testing/window.jpg")), new ImageIcon(getClass().getResource("/com/icon/testing/ryou.jpg")));
//        addItemLeft("Simpletext started as a passion project because I couldn’t find what I was looking for. Most apps were trying to do too much and ended up bloated with features I don’t need. So I built Simpletext based on a simple premise — what if there’s an app that refuses to do more, choosing instead to do just one thing, and do it well? For Simpletext, that one thing is writing.", "Tin", new ImageIcon(getClass().getResource("/com/icon/testing/frieren.jpg")), new ImageIcon(getClass().getResource("/com/icon/testing/ryou.jpg")));
//        addDate("05/06/2024");
//        String img[] = {"L~DT;*WBoyj@x|WXodayRPaij?WC", "LRC[:qF2XnIalCSi--NeL%jJM+S5"};
//        addItemRight("hello\nHi", img);
//        addItemLeft("hello\nerererew\newewe", "Kiki");
//        addItemRight("hello\nerererew\newewe", new ImageIcon(getClass().getResource("/com/icon/testing/ryou.jpg")));
//        addItemLeft("Hello this is my friend", "Tin", new ImageIcon(getClass().getResource("/com/icon/testing/frieren.jpg")), new ImageIcon(getClass().getResource("/com/icon/testing/frieren.jpg")));
//        addItemRight("Ok\nI'm Frieren");
//        addItemLeft("", "りょう", new ImageIcon(getClass().getResource("/com/icon/testing/ryou.jpg")));
//        addItemFile("", "Kiki", "mypdf.pdf", "2 MB");
//        addItemFileRight("", "Myfile.rar", "5 MB");
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(String text, String user, Icon... image) {
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setImage(image);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
    public void addItemFile(String text, String user, String fileName, String fileSize) {
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(String text, Icon... image) {
        ChatRight item = new ChatRight();
        item.setText(text);
        item.setImage(image);
        body.add(item, "wrap, al right, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
    public void addItemFileRight(String text, String fileName, String fileSize) {
        ChatRight item = new ChatRight();
        item.setText(text);
        item.setFile(fileName, fileSize);
        body.add(item, "wrap, al right, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
    public void addItemRight(String text, String[] image) {
        ChatRight item = new ChatRight();
        item.setText(text);
        item.setImage(image);
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
                        .addGap(0, 826, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
                bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 555, Short.MAX_VALUE)
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
                        .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
