
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public abstract class CreateSubTANButton extends NodeOptionButton<Cluster> {
    
    private final DisplayAbNAction displayTaxonomyListener;
    
    public CreateSubTANButton(
            String iconName,
            String toolTip,
            DisplayAbNAction displayTaxonomyListener) {
        
        super(iconName, toolTip);
        
        this.displayTaxonomyListener = displayTaxonomyListener;
        
        this.addActionListener((ae) -> {
            createAndDisplaySubTAN();
        });
    }
    
    public final void createAndDisplaySubTAN() {
        if (getCurrentNode().isPresent()) {
            DisplayNewSubTAN display = new DisplayNewSubTAN(this, displayTaxonomyListener, "Creating Sub TAN");
            display.startThread();
        }
    }
    
    public abstract ClusterTribalAbstractionNetwork createSubTAN();
}