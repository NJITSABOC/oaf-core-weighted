package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 * Base class for any class that represents the set of changes that affected
 * the classes between the FROM and TO releases of an ontology.
 * 
 * @author Chris O
 */
public abstract class OntologyChanges {
    
    private final Ontology fromOntology;
    private final Ontology toOntology;
    
    protected OntologyChanges(Ontology fromOntology, Ontology toOntology) {
        this.fromOntology = fromOntology;
        this.toOntology = toOntology;
    }
    
    public Ontology getFromOntology() {
        return fromOntology;
    }

    public Ontology getToOntology() {
        return toOntology;
    }
    
}
