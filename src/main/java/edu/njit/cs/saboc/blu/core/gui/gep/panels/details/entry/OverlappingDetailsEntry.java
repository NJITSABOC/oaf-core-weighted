package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class OverlappingDetailsEntry {
    
    private final Node overlappingNode;
    private final Set<DisjointNode> disjointGroups;
    
    public OverlappingDetailsEntry(Node overlappingNode, Set<DisjointNode> commonDisjointGroups) {
        this.overlappingNode = overlappingNode;
        this.disjointGroups = commonDisjointGroups;
    }
    
    public Node getOverlappingNode() {
        return overlappingNode;
    }
    
    public Set<DisjointNode> getDisjointGroups() {
        return disjointGroups;
    }
}
