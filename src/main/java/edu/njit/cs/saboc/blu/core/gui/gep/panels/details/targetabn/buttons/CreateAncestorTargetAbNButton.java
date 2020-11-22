package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris Ochs
 */
public class CreateAncestorTargetAbNButton extends CreateSubTargetAbNButton {
    
    private final TargetAbNConfiguration config;
    
    public CreateAncestorTargetAbNButton(
            TargetAbNConfiguration config,
            DisplayAbNAction displayAbNAction) {
        
        super("BluAncestorSubtaxonomy.png", 
                "Create an Ancestor Target Abstraction Network", 
                displayAbNAction);
        
        this.config = config;
    }

    @Override
    public TargetAbstractionNetwork createSubTargetAbN() {
        TargetGroup cluster = super.getCurrentNode().get();
        
        return config.getTargetAbstractionNetwork().createAncestorTargetAbN(cluster);
    }

    @Override
    public void setEnabledFor(TargetGroup group) {
        if(config.getTargetAbstractionNetwork().getNodeHierarchy().getParents(group).isEmpty()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }
}