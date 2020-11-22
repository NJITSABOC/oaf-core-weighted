package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a change to a concept between the FROM and TO versions of 
 * an ontology.
 * 
 * @author Chris O
 */
public class ConceptChange extends ConceptHierarchicalChange {
    
    /**
     * The state of the concept between the FROM and TO releases of the ontology
     */
    public static enum ConceptAddedRemovedChangeType {
        AddedToOnt,
        RemovedFromOnt,
        AddedToSubhierarchy,
        RemovedFromSubhierarchy
    }
    
    private final ConceptAddedRemovedChangeType changeType;
    
    private final Concept modifiedConcept;
    
    public ConceptChange(
            Concept affectedConcept, 
            ChangeInheritanceType changeInheritance, 
            Concept modifiedConcept,
            ConceptAddedRemovedChangeType changeType) {
        
        super(affectedConcept, changeInheritance);
        
        this.modifiedConcept = modifiedConcept;
        this.changeType = changeType;
    }
    
    public Concept getModifiedConcept() {
        return modifiedConcept;
    }
    
    public ConceptAddedRemovedChangeType getChangeType() {
        return changeType;
    }
}
