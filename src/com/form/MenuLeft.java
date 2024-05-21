package com.form;

import com.component.ItemPeople;
import net.miginfocom.swing.MigLayout;

public class MenuLeft extends javax.swing.JPanel {

    public MenuLeft() {
        initComponents();
        init();
    }
    
    private void init(){
        menuList.setLayout(new MigLayout("fillx", "0[]0", "0[]0"));
        showPeople();
    }
    
    private void showPeople(){
            //test data
            for(int i = 0; i < 20; i++){
                menuList.add(new ItemPeople("People " + i), "wrap");
            }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JLayeredPane();
        menuButton1 = new com.component.MenuButton();
        menuButton3 = new com.component.MenuButton();
        menuButton4 = new com.component.MenuButton();
        sp = new javax.swing.JScrollPane();
        menuList = new javax.swing.JLayeredPane();

        setBackground(new java.awt.Color(244, 244, 244));

        menu.setBackground(new java.awt.Color(229, 229, 229));
        menu.setOpaque(true);
        menu.setLayout(new javax.swing.BoxLayout(menu, javax.swing.BoxLayout.LINE_AXIS));

        menuButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/message_selected.png"))); // NOI18N
        menuButton1.setPreferredSize(new java.awt.Dimension(66, 42));
        menu.add(menuButton1);

        menuButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/group.png"))); // NOI18N
        menuButton3.setPreferredSize(new java.awt.Dimension(66, 42));
        menu.add(menuButton3);

        menuButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/icon/box.png"))); // NOI18N
        menuButton4.setPreferredSize(new java.awt.Dimension(66, 42));
        menu.add(menuButton4);

        javax.swing.GroupLayout menuListLayout = new javax.swing.GroupLayout(menuList);
        menuList.setLayout(menuListLayout);
        menuListLayout.setHorizontalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        menuListLayout.setVerticalGroup(
            menuListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        sp.setViewportView(menuList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane menu;
    private com.component.MenuButton menuButton1;
    private com.component.MenuButton menuButton3;
    private com.component.MenuButton menuButton4;
    private javax.swing.JLayeredPane menuList;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
