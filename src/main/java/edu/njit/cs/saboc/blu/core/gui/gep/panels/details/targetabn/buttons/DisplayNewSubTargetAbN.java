package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris Ochs
 */
public class DisplayNewSubTargetAbN extends CreateAndDisplayAbNThread {

    private final CreateSubTargetAbNButton btn;

    public DisplayNewSubTargetAbN(
            CreateSubTargetAbNButton btn, 
            DisplayAbNAction display, 
            String displayText) {
        
        super(displayText, display);
        
        this.btn = btn;
    }

    @Override
    public AbstractionNetwork getAbN() {
        return btn.createSubTargetAbN();
    }
}