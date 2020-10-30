package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class PartitionNodeOverlappingConceptTableModel<T extends PartitionedNode> extends AbstractNodeEntityTableModel<Concept, T> {
    
    private final PartitionedAbNConfiguration config;
    
    private Optional<Map<Concept, Set<SinglyRootedNode>>> currentPartitionedNodeConcepts = Optional.empty();
    
    public PartitionNodeOverlappingConceptTableModel(PartitionedAbNConfiguration config) {
        super(new String [] { 
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false),
            String.format("Overlapping", config.getTextConfiguration().getNodeTypeName(true))
        });
        
        this.config = config;
    }

    @Override
    public void clearCurrentNode() {
        super.clearCurrentNode();
        
        currentPartitionedNodeConcepts = Optional.empty();
    }

    @Override
    public void setCurrentNode(T node) {
        super.setCurrentNode(node);

        this.currentPartitionedNodeConcepts = Optional.of(node.getConceptNodes());
    }

    @Override
    protected Object[] createRow(Concept item) {
        
        int nodeCount = -1;
        
        if (currentPartitionedNodeConcepts.isPresent()) {
            nodeCount = currentPartitionedNodeConcepts.get().get(item).size();
        } 
        
        String overlapStr;
        
        if(nodeCount == 1) {
            overlapStr = "No";
        } else {
            overlapStr = String.format("Yes (%d)", nodeCount);
        }
        
        return new Object[]{
                item,
                overlapStr
            };
    }
}
