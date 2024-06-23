package com.view.swingController;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.accessibility.AccessibleContext;

public class ActiveStatus extends Component {

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        repaint();
    }

    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    public void setAccessibleContext(AccessibleContext accessibleContext) {
        this.accessibleContext = accessibleContext;
    }

    public ActiveStatus() {
        setPreferredSize(new Dimension(8, 8));
    }

    public void paint(Graphics g) {
        if (active) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(77, 255, 60));
            g2.fillOval(0, (getHeight() / 2) - 4, 8, 8);
        }
    }
}
