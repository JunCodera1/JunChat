package com.component;

import com.swing.ScrollBar;
import java.awt.Color;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;

public class ChatBody extends javax.swing.JPanel {

    public ChatBody() {
        initComponents();
        init();
        addItemRight("Dịch vụ của Google, được cung cấp miễn phí, dịch nhanh các từ, cụm từ và trang web giữa tiếng Anh và hơn 100 ngôn ngữ khác.Hello there ! it's really nice project but i'm facing issue in resizing the screen ....the home page doesn't move according to the Main screen .... kindly please tell me what to do ...");
        addItemRight("Dịch vụ của Google, được cung cấp miễn phí, dịch nhanh các từ, cụm từ và trang web giữa tiếng Anh và hơn 100 ngôn ngữ khác.Hello there ! it's really nice project but i'm facing issue in resizing the screen ....the home page doesn't move according to the Main screen .... kindly please tell me what to do ...");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemRight("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemRight("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemRight("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemRight("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemRight("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemRight("Hello\nfweijfwiefjwo\nưefjifweof");
        addItemLeft("Hello\nfweijfwiefjwo\nưefjifweof");
       

    }
    
    private void init(){
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }
    
    public void addItemLeft(String text){
        ChatLeft item = new ChatLeft();
        item.setText(text);
        body.add(item , "wrap, w ::80%");
        // ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
    public void addItemRight(String text){
        ChatRight item = new ChatRight();
        item.setText(text);
        body.add(item , "wrap, al right , w ::80%");
        // ::80% set max with 80%
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
