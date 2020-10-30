package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Cros
 */

public class CreateRootTANButton extends CreateSubTANButton {
    
    private final TANConfiguration config;
    
    public CreateRootTANButton(TANConfiguration config,
            DisplayAbNAction<? extends ClusterTribalAbstractionNetwork> displayTaxonomyListener) {
        super("BluSubtaxonomy.png", "Create Root Sub TAN", displayTaxonomyListener);
        
        this.config = config;
    }

    @Override
    public ClusterTribalAbstractionNetwork createSubTAN() {
        Cluster cluster = (Cluster)super.getCurrentNode().get();
        
        return config.getTribalAbstractionNetwork().createRootSubTAN(cluster);
    }

    @Override
    public void setEnabledFor(Cluster cluster) {
        
        if(config.getTribalAbstractionNetwork().getClusterHierarchy().getChildren(cluster).isEmpty()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }
}