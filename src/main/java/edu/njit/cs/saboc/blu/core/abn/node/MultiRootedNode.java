
package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a node that has multiple root concepts
 * 
 * @author Chris O
 */
public abstract class MultiRootedNode extends Node {
    
    private final Hierarchy<Concept> hierarchy;
    
    public MultiRootedNode(Hierarchy<Concept> hierarchy) {
        this.hierarchy = hierarchy;
    }
    
    public Hierarchy<Concept> getHierarchy() {
        return hierarchy;
    }
    
    public Set<Concept> getRoots() {
        return hierarchy.getRoots();
    }
}
