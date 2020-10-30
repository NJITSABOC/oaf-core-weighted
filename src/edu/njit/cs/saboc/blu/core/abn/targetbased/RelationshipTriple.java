
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Objects;

/**
 * Represents a (source, relationship type, target) triple
 * 
 * @author Chris O
 */
public class RelationshipTriple {
    
    private final Concept source;
    private final InheritableProperty property;
    private final Concept target;

    public RelationshipTriple(Concept source, InheritableProperty property, Concept target) {
        this.source = source;
        this.property = property;
        this.target = target;
    }
    
    public Concept getSource() {
        return source;
    }

    public InheritableProperty getRelationship() {
        return property;
    }

    public Concept getTarget() {
        return target;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        
        hash = 47 * hash + Objects.hashCode(this.source);
        hash = 47 * hash + Objects.hashCode(this.property);
        hash = 47 * hash + Objects.hashCode(this.target);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final RelationshipTriple other = (RelationshipTriple) obj;
        
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        
        if (!Objects.equals(this.property, other.property)) {
            return false;
        }
        
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        
        return true;
    }
    
    
}
