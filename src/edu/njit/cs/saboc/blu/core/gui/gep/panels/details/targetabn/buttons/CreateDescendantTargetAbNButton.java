package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris Ochs
 */
public class CreateDescendantTargetAbNButton extends CreateSubTargetAbNButton {
    
    private final TargetAbNConfiguration config;
    
    public CreateDescendantTargetAbNButton(
            TargetAbNConfiguration config,
            DisplayAbNAction displayAbNAction) {
        
        super("BluSubtaxonomy.png", 
                "Create Descendant Target AbN", 
                displayAbNAction);
        
        this.config = config;
    }

    @Override
    public TargetAbstractionNetwork createSubTargetAbN() {
        TargetGroup group = super.getCurrentNode().get();
        
        return config.getTargetAbstractionNetwork().createDescendantTargetAbN(group);
    }

    @Override
    public void setEnabledFor(TargetGroup group) {
        
        if(config.getTargetAbstractionNetwork().getNodeHierarchy().getChildren(group).isEmpty()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }
}