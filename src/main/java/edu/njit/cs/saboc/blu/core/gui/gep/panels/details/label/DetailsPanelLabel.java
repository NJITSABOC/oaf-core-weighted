package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class DetailsPanelLabel extends JPanel {
    
    private final JLabel lblText;
    
    public DetailsPanelLabel(String label) {
        super(new BorderLayout());
        
        lblText = new JLabel("<html>" + label);
        lblText.setFont(lblText.getFont().deriveFont(Font.BOLD, 20));
        
        this.add(Box.createVerticalStrut(2), BorderLayout.NORTH);
        this.add(Box.createHorizontalStrut(8), BorderLayout.WEST);
        this.add(lblText, BorderLayout.CENTER);
        this.add(Box.createVerticalStrut(2), BorderLayout.SOUTH);
    }
    
    public void setText(String label) {
        lblText.setText("<html>" + label);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.BLACK);

        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
        g2d.fillRect(getWidth() / 2, 0, getWidth() / 2, getHeight());

        g2d.setColor(Color.WHITE);

        final int BORDER_SIZE = 2;

        g2d.fillRoundRect(BORDER_SIZE, BORDER_SIZE, getWidth() - (2 * BORDER_SIZE), getHeight() - (2 * BORDER_SIZE), 16, 16);
        g2d.fillRect(getWidth() / 2, BORDER_SIZE, getWidth() / 2, getHeight() - (2 * BORDER_SIZE));

        g2d.setFont(getFont());
        g2d.setColor(Color.BLACK);

        g.drawImage(bi, 0, 0, null);
    }
}
