package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Objects;

/**
 * Captures a hierarchical change that affected the inheritance of an 
 * inheritable property.
 * 
 * @author Chris O
 */
public class InheritablePropertyHierarchyChange extends InheritablePropertyChange {

    /**
     * The state of a hierarchical connection (i.e., Is a or subclassOf) between
     * two concepts
     */
    public static enum HierarchicalConnectionState {
        Added,
        Removed
    }
    
    private final Concept parent;
    
    private final HierarchicalConnectionState hierarchicalConnectionState;

    public InheritablePropertyHierarchyChange(
            Concept child,
            Concept parent,
            InheritableProperty property,
            HierarchicalConnectionState hierarchicalConnectionState,
            ChangeInheritanceType inheritanceType) {

        super(child, property, inheritanceType);
        
        this.parent = parent;
        this.hierarchicalConnectionState = hierarchicalConnectionState;
    }

    public Concept getParent() {
        return parent;
    }
    
    public Concept getChild() {
        return super.getAffectedConcept();
    }

    public HierarchicalConnectionState getHierarchicalConnectionState() {
        return hierarchicalConnectionState;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        
        hash = 97 * hash + Objects.hashCode(this.parent);
        hash = 97 * hash + Objects.hashCode(this.getParent());
        hash = 97 * hash + Objects.hashCode(this.hierarchicalConnectionState);
        
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
        
        final InheritablePropertyHierarchyChange other = (InheritablePropertyHierarchyChange) obj;
        
        if(!this.getChild().equals(other.getChild())) {
            return false;
        }
        
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        
        if (!Objects.equals(this.getProperty(), other.getProperty())) {
            return false;
        }
        
        if (this.hierarchicalConnectionState != other.hierarchicalConnectionState) {
            return false;
        }
        
        return true;
    }
}
