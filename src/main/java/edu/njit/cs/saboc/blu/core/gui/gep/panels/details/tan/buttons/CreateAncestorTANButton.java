package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateAncestorTANButton extends CreateSubTANButton {
    
    private final TANConfiguration config;
    
    public CreateAncestorTANButton(TANConfiguration config,
            DisplayAbNAction displayTaxonomyListener) {
        super("BluAncestorSubtaxonomy.png", "Create an ancestor TAN", displayTaxonomyListener);
        
        this.config = config;
    }

    @Override
    public ClusterTribalAbstractionNetwork createSubTAN() {
        Cluster cluster = super.getCurrentNode().get();
        
        return config.getTribalAbstractionNetwork().createAncestorTAN(cluster);
    }

    @Override
    public void setEnabledFor(Cluster cluster) {
        if(config.getTribalAbstractionNetwork().getClusterHierarchy().getParents(cluster).isEmpty()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }
}