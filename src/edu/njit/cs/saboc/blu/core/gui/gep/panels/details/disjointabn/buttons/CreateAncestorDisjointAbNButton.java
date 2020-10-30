
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateAncestorDisjointAbNButton<T extends DisjointNode> extends NodeOptionButton<T> {
    
    private final DisplayAbNAction<DisjointAbstractionNetwork> displayDisjointAbNListener;
    
    private final  DisjointAbNConfiguration config;
    
    public CreateAncestorDisjointAbNButton(
            DisjointAbNConfiguration config, 
            DisplayAbNAction<DisjointAbstractionNetwork> displayDisjointAbNListener) {
        
        super("BluAncestorSubtaxonomy.png", 
                String.format("View %s Ancestors", config.getTextConfiguration().getNodeTypeName(false)));
        
        this.config = config;
        this.displayDisjointAbNListener = displayDisjointAbNListener;
        
        this.addActionListener((ae) -> {
            createAndDisplayAncestorAbN();
        });
    }
    
    public final void createAndDisplayAncestorAbN() {
        if (getCurrentNode().isPresent()) {

            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread("Displaying ancestors...", 
                    displayDisjointAbNListener) {

                @Override
                public AbstractionNetwork getAbN() {
                    return config.getAbstractionNetwork().getAncestorDisjointAbN(getCurrentNode().get());
                }
            };

            display.startThread();
        }
    }

    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(!config.getAbstractionNetwork().getNodeHierarchy().getParents(node).isEmpty());
    }
}
