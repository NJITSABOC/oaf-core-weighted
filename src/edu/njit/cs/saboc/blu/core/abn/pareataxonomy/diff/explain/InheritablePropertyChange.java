package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A change to an inheritable property that affected a concept.
 * 
 * @author Chris O
 */
public class InheritablePropertyChange extends DiffAbNConceptChange {
        
    private final InheritableProperty property;
    
    public InheritablePropertyChange(
            Concept affectedConcept, 
            InheritableProperty property, 
            ChangeInheritanceType inheritanceType) {
        
        super(affectedConcept, inheritanceType);
        
        this.property = property;
    }
    
    public final InheritableProperty getProperty() {
        return property;
    }
}
