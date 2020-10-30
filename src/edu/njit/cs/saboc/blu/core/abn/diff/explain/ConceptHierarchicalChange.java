package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a change to a concept's hierarchical relationships or a change
 * to an ancestor concept's hierarchical relationships (if inherited)
 * 
 * @author Chris O
 */
public class ConceptHierarchicalChange extends DiffAbNConceptChange {

    public ConceptHierarchicalChange(
            Concept affectedConcept,
            ChangeInheritanceType changeInheritance) {
        
        super(affectedConcept, changeInheritance);
    }
}
