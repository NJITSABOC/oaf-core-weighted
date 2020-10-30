package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class HighlightOverlappingButton<T extends PartitionedAbstractionNetwork<?, ?>> extends AbNOptionsButton<T> {
    
    private final PartitionedAbNConfiguration config;
    
    public HighlightOverlappingButton(PartitionedAbNConfiguration config) {
        
        super("BluDisjointAbN.png", 
                String.format("Highlight %s with overlapping %s", 
                        config.getTextConfiguration().getNodeTypeName(true).toLowerCase(),
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase()));
        
        this.config = config;
        
        this.addActionListener( (ae) -> {
            highlightOverlappingNodes();
        });
    }
    
    private void highlightOverlappingNodes() {
        Set<SinglyRootedNode> overlappingNodes = (Set<SinglyRootedNode>)this.getCurrentAbN().get().getOverlappingNodes();
        
        config.getUIConfiguration().getDisplayPanel().highlightSinglyRootedNodes(overlappingNodes);
    }

    @Override
    public void setEnabledFor(T entity) {
        boolean hasOverlapping = entity.getBaseAbstractionNetwork().getNodes().stream().anyMatch( (partitionedNode) -> {
            return partitionedNode.hasOverlappingConcepts();
        });
        
        this.setEnabled(hasOverlapping);
    }
}