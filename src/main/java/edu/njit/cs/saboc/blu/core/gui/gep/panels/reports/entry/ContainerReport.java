package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Den
 */
public class ContainerReport {
    
    private final PartitionedNode container;
    
    private final Set<SinglyRootedNode> groups;
    private final Set<Concept> concepts;
    private final Set<Concept> overlappingConcepts;

    public ContainerReport(
            PartitionedNode container, 
            Set<SinglyRootedNode> groups, 
            Set<Concept> concepts, 
            Set<Concept> overlappingConcepts) {
        
        this.container = container;
        
        this.groups = groups;
        this.concepts = concepts;
        this.overlappingConcepts = overlappingConcepts;
    }
    
    public PartitionedNode getContainer() {
        return container;
    }

    public Set<SinglyRootedNode> getGroups() {
        return groups;
    }

    public Set<Concept> getConcepts() {
        return concepts;
    }

    public Set<Concept> getOverlappingConcepts() {
        return overlappingConcepts;
    }
}
