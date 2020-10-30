package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a partial-area, a subhierarchy of concepts that are modeled
 * with the same types of inheritable properties
 * 
 * @author Chris O
 */
public class PArea extends SinglyRootedNode {
    
    private final Set<InheritableProperty> relationships;
    
    public PArea(
            Hierarchy<Concept> conceptHierarchy, 
            Set<InheritableProperty> relationships) {
        
        super(conceptHierarchy);
        
        this.relationships = relationships;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof PArea) {
            PArea other = (PArea)o;
            
            return super.equals(other) && this.getRelationships().equals(other.getRelationships());
        }
        
        return false;
    }
    
    public Set<InheritableProperty> getRelationships() {
        return relationships;
    }
}
