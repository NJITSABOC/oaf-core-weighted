package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a change in a concept's parent concepts or changes to 
 * an ancestor's parent concepts (if the change is inherited).
 * 
 * @author Chris O
 */
public class ConceptParentChange extends ConceptHierarchicalChange {
    
    /**
     * The state of the parent in the TO release of the ontology relative to 
     * the FROM release.
     */
    public static enum ParentState {
        Added,
        Removed
    }

    private final ParentState parentState;
    private final Concept parent;
    
    private final Concept modifiedConcept;
    
    public ConceptParentChange(
            Concept affectedConcept, 
            Concept modifiedConcept, 
            ChangeInheritanceType inheritance, 
            Concept parent, 
            ParentState parentState) {
        
        super(affectedConcept, inheritance);
        
        this.parent = parent;
        this.parentState = parentState;
        this.modifiedConcept = modifiedConcept;
    }
    
    public Concept getParent() {
        return parent;
    }
    
    public Concept getModifiedConcept() {
        return modifiedConcept;
    }
    
    public ParentState getParentState() {
        return parentState;
    }
}
