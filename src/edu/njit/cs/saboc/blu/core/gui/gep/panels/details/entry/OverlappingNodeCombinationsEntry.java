package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeCombinationsEntry {

    private final Set<DisjointNode> disjointNodes;
    
    private final Node overlappingSource;
    
    public OverlappingNodeCombinationsEntry(Node overlappingSource, Set<DisjointNode> disjointNodes) {
        this.overlappingSource = overlappingSource;
        this.disjointNodes = disjointNodes;
    }
    
    public Set<Node> getOtherOverlappingNodes() {
        Set<Node> groups = new HashSet<>(disjointNodes.iterator().next().getOverlaps());
        groups.remove(overlappingSource);
        
        return groups;
    }
    
    public Node getOverlappingSource() {
        return overlappingSource;
    }
    
    public Set<DisjointNode> getDisjointNodes() {
        return disjointNodes;
    }
    
    public Set<Concept> getOverlappingConcepts() {
        
        Set<Concept> overlappingConcepts = new HashSet<>();
        
        disjointNodes.forEach( (DisjointNode group) -> {
            overlappingConcepts.addAll(group.getConcepts());
        });

        return overlappingConcepts;
    }
}
