package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

/**
 *
 * @author Chris O
 */
public class ResetHighlightsPanel extends AbNDisplayWidget {
    
     private final Dimension panelSize = new Dimension(120, 50);
    
    public ResetHighlightsPanel(AbNDisplayPanel panel) {
        super(panel);
        
        this.setLayout(new BorderLayout());
        
        JButton resetHighlightsButton = new JButton("<html><div align='center'>Clear Highlights");
        resetHighlightsButton.addActionListener( (ae) -> {
            panel.getAbNPainter().clearHighlights();
        });
        
        resetHighlightsButton.setForeground(Color.RED);
        resetHighlightsButton.setBackground(new Color(240, 240, 255));
        
        this.add(resetHighlightsButton, BorderLayout.CENTER);
    }

    @Override
    public void update(int tick) {
        AbNDisplayPanel panel = super.getDisplayPanel();
        
        if(panel.getAbNPainter().showingHighlights()) {
            this.setVisible(true);
        } else {
            this.setVisible(false);
        }
    }
    
    public void draw(AbNDisplayPanel displayPanel){
        
        this.setBounds(
                displayPanel.getWidth() - 150, 
                displayPanel.getHeight() - panelSize.height - 200, 
                panelSize.width, 
                panelSize.height);
        
        displayPanel.add(this);
        
        if(displayPanel.getAbNPainter().showingHighlights()) {
            this.setVisible(true);
        } else {
            this.setVisible(false);
            
            displayPanel.remove(this);
        }        
    }
    
    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        
        this.setBounds(
                displayPanel.getWidth() - 150, 
                displayPanel.getHeight() - panelSize.height - 150, 
                panelSize.width, 
                panelSize.height);
    }
}
