package com.emoji;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ModelEmoji {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public ModelEmoji(int id, Icon icon) {
        this.id = id;
        this.icon = icon;
    }
    
    public ModelEmoji() {
    }
    
    private int id;
    private Icon icon;
    
    public ModelEmoji toSize(int x, int y){
        return new ModelEmoji(id, new ImageIcon(((ImageIcon)icon).getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH)));
    }
}
