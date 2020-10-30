package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a structural change to a concept between the FROM and TO
 * releases of an ontology or a structural chance to an ancestor (if inherited)
 * 
 * @author Chris O
 */
public class DiffAbNConceptChange {
    
    public static enum ChangeInheritanceType {
        Direct,
        Indirect
    }
    
    private final Concept affectedConcept;
    private final ChangeInheritanceType changeInheritance;
    
    public DiffAbNConceptChange(Concept affectedConcept, 
            ChangeInheritanceType changeInheritance) {
        
        this.affectedConcept = affectedConcept;
        this.changeInheritance = changeInheritance;
    }
    
    public Concept getAffectedConcept() {
        return affectedConcept;
    }
    
    public ChangeInheritanceType getChangeInheritanceType() {
        return changeInheritance;
    }
}
