package com.component;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

public class OptionButton extends JButton{
    
    
    public OptionButton(){
        setContentAreaFilled(false);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    @Override
    public void setSelected(boolean bln) {
        super.setSelected(bln); 
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics grphcs){
        super.paintComponent(grphcs); 
        if(isSelected()){
            grphcs.setColor(new Color(57, 239, 76));
            grphcs.fillRect(0, getHeight() - 3, getWidth(), getHeight());
        }
    }
    
    
}
