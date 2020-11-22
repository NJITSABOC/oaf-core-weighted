package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.HashSet;
import java.util.Set;

/**
 * A factory class for generating relationship subtaxonomies, which are partial-area
 * taxonomies created using a subset of the inheritable properties used to define
 * the concepts
 * 
 * @author cro3
 */
public class PAreaRelationshipSubtaxonomyFactory extends PAreaTaxonomyFactory {
    
    private final PAreaTaxonomy baseTaxonomy;
    private final Set<InheritableProperty> allowedRels;
    
    public PAreaRelationshipSubtaxonomyFactory(
            Ontology sourceOntology,
            PAreaTaxonomy baseTaxonomy, 
            Set<InheritableProperty> allowedTypes) {
        
        super(sourceOntology);

        this.baseTaxonomy = baseTaxonomy;
        this.allowedRels = allowedTypes;
    }
    
    @Override
    public AreaTaxonomy createAreaTaxonomy(
            Hierarchy<Area> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {

        return baseTaxonomy.getPAreaTaxonomyFactory().createAreaTaxonomy(
                areaHierarchy, 
                sourceHierarchy);
    }
    
    @Override
    public <T extends PArea> PAreaTaxonomy createPAreaTaxonomy(
            AreaTaxonomy areaTaxonomy,
            Hierarchy<T> pareaHierarchy, 
            Hierarchy<Concept> conceptHierarchy) {

        return new RelationshipSubtaxonomy(
                baseTaxonomy, 
                allowedRels, 
                super.createPAreaTaxonomy(areaTaxonomy, pareaHierarchy, conceptHierarchy));
    }
    
    @Override
    public Set<InheritableProperty> getRelationships(Concept c) {
        Set<InheritableProperty> allRels = new HashSet<>(baseTaxonomy.getPAreaTaxonomyFactory().getRelationships(c));
        
        allRels.retainAll(allowedRels);

        return allRels;
    }
}
