package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Generic class representing a node in an abstraction network
 * 
 * @author Chris O
 */
public abstract class Node {
    
    protected Node() {
        
    }

    public abstract int getConceptCount();
    public abstract String getName();
    public abstract Set<Concept> getConcepts();
    
    /**
     * Determines if two nodes represent the same SIMILARITY.
     * No guarantees are made regarding the set of concepts being the same.
     * 
     * Use strictEquals to guarantee the sets of concepts summarized by the node
     * are the same.
     * 
     * @param o
     * @return 
     */
    @Override
    public abstract boolean equals(Object o);
    
    /**
     * The two nodes are equal and the set of concepts they summarize is equal
     * @param o
     * @return 
     */
    public boolean strictEquals(Node o) {
        return this.equals(o) && this.getConcepts().equals(o.getConcepts());
    }
    
    @Override
    public abstract int hashCode();
    
    @Override
    public String toString() {
        return getName();
    }
}
