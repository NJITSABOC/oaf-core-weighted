package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * An abstraction network node that consists of a singly rooted hierarchy of concepts.
 * 
 * Examples include partial-areas, clusters, disjoint nodes, etc
 * 
 * @author Chris O
 */
public abstract class SinglyRootedNode extends Node {
    
    private final Hierarchy<Concept> hierarchy;

    protected SinglyRootedNode(Hierarchy<Concept> hierarchy) {
        this.hierarchy = hierarchy;
    }
    
    public Hierarchy<Concept> getHierarchy() {
        return hierarchy;
    }
    
    public Concept getRoot() {
        return hierarchy.getRoot();
    }
    
    public Set<Concept> getConcepts() {
        return hierarchy.getNodes();
    }
        
    public boolean equals(Object o) {
        if(o instanceof SinglyRootedNode) {
            SinglyRootedNode other = (SinglyRootedNode)o;
            
            return this.getRoot().equals(other.getRoot());
        }
        
        return false;
    }
    
    public int hashCode() {
        return this.getRoot().hashCode();
    }
    
    public int getConceptCount() {
        return hierarchy.size();
    }
    
    public String getName() {
        return String.format("%s", getRoot().getName());
    }
}
