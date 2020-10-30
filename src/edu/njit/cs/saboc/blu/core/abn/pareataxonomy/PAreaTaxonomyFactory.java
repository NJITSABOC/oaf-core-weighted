package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;

/**
 * A factory class for creating implementation-specific partial-area taxonomy
 * objects
 * 
 * @author Chris O
 */
public abstract class PAreaTaxonomyFactory {
    
    private final Ontology sourceOntology;
    
    public PAreaTaxonomyFactory(Ontology sourceOntology) {
        this.sourceOntology = sourceOntology;
    }
    
    public Ontology getSourceOntology() {
        return sourceOntology;
    }
    
    public AreaTaxonomy createAreaTaxonomy(
            Hierarchy<Area> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        return new AreaTaxonomy(
                this, 
                areaHierarchy, 
                sourceHierarchy);
    }
    
    public <T extends PArea> PAreaTaxonomy createPAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {
        
        return new PAreaTaxonomy(
                areaTaxonomy, 
                pareaHierarchy, 
                conceptHierarchy);
    }

    public abstract Set<InheritableProperty> getRelationships(Concept c);
}
