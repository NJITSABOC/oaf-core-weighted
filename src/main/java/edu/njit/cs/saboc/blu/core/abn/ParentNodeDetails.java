
package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a link between two singly rooted nodes. Specifically, if A is
 * CHILD-OF B then this class represents the link from A's root to the concept
 * in B.
 * 
 * @author cro3
 */
public class ParentNodeDetails<T extends Node> {
    private final Concept parentConcept;
    private final T parentNode;
    
    public ParentNodeDetails(Concept parentConcept, T parentNode) {
        this.parentConcept = parentConcept;
        this.parentNode = parentNode;
    }
    
    public Concept getParentConcept() {
        return parentConcept;
    }
    
    public T getParentNode() {
        return parentNode;
    }
}
