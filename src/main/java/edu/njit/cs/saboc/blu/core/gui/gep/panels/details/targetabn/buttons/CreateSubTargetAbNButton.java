package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons.DisplayNewSubTAN;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris Ochs
 */
public abstract class CreateSubTargetAbNButton extends NodeOptionButton<TargetGroup> {
    
    private final DisplayAbNAction displayTargetAbNAction;
    
    public CreateSubTargetAbNButton(
            String iconName,
            String toolTip,
            DisplayAbNAction displayTargetAbNAction) {
        
        super(iconName, toolTip);
        
        this.displayTargetAbNAction = displayTargetAbNAction;
        
        this.addActionListener( (ae) -> {
            createAndDisplaySubTAN();
        });
    }
    
    public final void createAndDisplaySubTAN() {
        if (getCurrentNode().isPresent()) {
            
            DisplayNewSubTargetAbN display = new DisplayNewSubTargetAbN(
                    this, 
                    displayTargetAbNAction, 
                    "Creating Target Abstraction Network");
            
            display.startThread();
        }
    }
    
    public abstract TargetAbstractionNetwork createSubTargetAbN();
}