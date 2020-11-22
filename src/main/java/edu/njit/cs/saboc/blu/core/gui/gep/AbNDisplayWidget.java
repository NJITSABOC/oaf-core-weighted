package edu.njit.cs.saboc.blu.core.gui.gep;

import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNDisplayWidget extends JPanel implements UpdateableAbNDisplayEntity {
    
    private final AbNDisplayPanel displayPanel;
    
    public AbNDisplayWidget(AbNDisplayPanel parentDisplayPanel) {
        this.displayPanel = parentDisplayPanel;
    }
    
    public AbNDisplayPanel getDisplayPanel() {
        return displayPanel;
    }
    
    public void initialize() {
        
    }

    @Override
    public void update(int tick) {

    }

    @Override
    public void initialize(AbNDisplayPanel displayPanel) {

    }
    
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        
    }
}
