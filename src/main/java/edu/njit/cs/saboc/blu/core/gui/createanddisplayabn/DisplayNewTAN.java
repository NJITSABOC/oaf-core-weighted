package edu.njit.cs.saboc.blu.core.gui.createanddisplayabn;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.CreateTANButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

public class DisplayNewTAN extends CreateAndDisplayAbNThread {

    private final CreateTANButton btn;

    public DisplayNewTAN(CreateTANButton btn, DisplayAbNAction display, String displayText) {
        super(displayText, display);
        
        this.btn = btn;
    }

    @Override
    public AbstractionNetwork getAbN() {
        return btn.deriveTAN();
    }
}
