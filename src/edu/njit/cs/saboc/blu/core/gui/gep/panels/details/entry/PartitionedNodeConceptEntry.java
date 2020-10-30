package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PartitionedNodeConceptEntry {
    
    private final Concept concept;
    private final Set<SinglyRootedNode> subNodes;
    
    public PartitionedNodeConceptEntry(Concept concept, Set<SinglyRootedNode> groups) {
        this.concept = concept;
        this.subNodes = groups;
    }
    
    public Concept getConcept() {
        return concept;
    }
    
    public Set<SinglyRootedNode> getNodes() {
        return subNodes;
    }
}
