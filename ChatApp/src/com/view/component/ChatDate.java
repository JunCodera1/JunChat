package com.view.component;
  
public class ChatDate extends javax.swing.JLayeredPane {

    public ChatDate() {
        initComponents();
    }
    
    public void setDate(String date){
        lbDate.setText(String.valueOf(java.time.LocalDate.now()));
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbDate = new javax.swing.JLabel();
        line1 = new com.view.swingController.Line();
        line2 = new com.view.swingController.Line();

        lbDate.setForeground(new java.awt.Color(102, 102, 102));
        lbDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDate.setText("13/05/2024");

        line1.setForeground(new java.awt.Color(102, 102, 102));

        line2.setForeground(new java.awt.Color(102, 102, 102));

        setLayer(lbDate, javax.swing.JLayeredPane.DEFAULT_LAYER);
        setLayer(line1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        setLayer(line2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(line1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(line2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbDate;
    private com.view.swingController.Line line1;
    private com.view.swingController.Line line2;
    // End of variables declaration//GEN-END:variables
}
