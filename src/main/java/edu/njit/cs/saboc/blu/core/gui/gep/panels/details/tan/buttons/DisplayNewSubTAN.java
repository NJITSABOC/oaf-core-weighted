package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

public class DisplayNewSubTAN extends CreateAndDisplayAbNThread {

    private final CreateSubTANButton btn;

    public DisplayNewSubTAN(CreateSubTANButton btn, DisplayAbNAction display, String displayText) {
        super(displayText, display);
        this.btn = btn;
    }

    @Override
    public AbstractionNetwork getAbN() {
        return btn.createSubTAN();
    }

}
