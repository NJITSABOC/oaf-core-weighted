package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons;

import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class DerivationSelectionButton extends PopupToggleButton {
    
    public DerivationSelectionButton(
            MultiAbNGraphFrame graphFrame, 
            JPanel derivationWizardPanel) {
        
        super(graphFrame.getParentFrame(), "Create New Summary");
        
        this.setPopupContent(derivationWizardPanel);
    }
}
