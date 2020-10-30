package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.SimplePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * An abstraction network that consists of a hierarchy of areas
 * 
 * @author Chris O
 * @param <T>
 */
public class AreaTaxonomy<T extends Area> extends AbstractionNetwork<T> {
    
    private static SimplePAreaTaxonomyDerivation makeDerivation(
            PAreaTaxonomyFactory factory, 
            Concept root) {
        
        SimplePAreaTaxonomyDerivation derivation = new SimplePAreaTaxonomyDerivation(
                        root, 
                        factory);
        
        return derivation;
    }
    
    // The factory that was used to create this area taxonomy
    private final PAreaTaxonomyFactory factory;
    
    public AreaTaxonomy(
            PAreaTaxonomyFactory factory,
            Hierarchy<T> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy,
            PAreaTaxonomyDerivation derivation) {
        
        super(areaHierarchy, sourceHierarchy, derivation);
        
        this.factory = factory;
    }
    
    public AreaTaxonomy(
            PAreaTaxonomyFactory factory,
            Hierarchy<T> areaHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        this(factory,
                areaHierarchy, 
                sourceHierarchy, 
                AreaTaxonomy.makeDerivation(factory, sourceHierarchy.getRoot()));
    }
    
    @Override
    public PAreaTaxonomyDerivation getDerivation() {
        return (PAreaTaxonomyDerivation)super.getDerivation();
    }
    
    public PAreaTaxonomyFactory getPAreaTaxonomyFactory() {
        return factory;
    }
    
    public Hierarchy<T> getAreaHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public Set<T> getAreas() {
        return super.getNodes();
    }

    @Override
    public Set<T> searchNodes(String query) {
        return findAreas(query);
    }
        
    /**
     * Returns the set of areas that have inheritable properties 
     * with names that match the given comma-delimited query
     * 
     * @param query
     * @return 
     */
    public Set<T> findAreas(String query) {
        
        query = query.toLowerCase();
        
        Set<T> searchResults = new HashSet<>();

        Set<T> areas = this.getAreas();

        String[] searchedRels = query.split(", ");

        if (searchedRels == null) {
            return searchResults;
        }

        for (T area : areas) {
            ArrayList<String> relsInArea = new ArrayList<>();
            
            Set<InheritableProperty> areaRels = area.getRelationships();
            
            areaRels.forEach((rel) -> {
                relsInArea.add(rel.getName().toLowerCase());
            });

            boolean allRelsFound = true;

            for (String rel : searchedRels) {

                boolean relFound = false;

                for (String areaRel : relsInArea) {
                    if (areaRel.toLowerCase().contains(rel)) {
                        relFound = true;
                        break;
                    }
                }

                if (!relFound) {
                    allRelsFound = false;
                    break;
                }
            }

            if (allRelsFound) {
                searchResults.add(area);
            }
        }

        return searchResults;
    }

    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T area) {
        return AbstractionNetworkUtils.getMultiRootedNodeParentNodeDetails(
                area, 
                this.getSourceHierarchy(), 
                (Set<PartitionedNode>)(Set<?>)this.getAreas());
    }
    
    /**
     * Returns the set of inheritable properties that exist for concepts summarized by 
     * this area taxonomy
     * 
     * @return 
     */
    public Set<InheritableProperty> getPropertiesInTaxonomy() {
        Set<InheritableProperty> allProperties = new HashSet<>();
        
        getAreas().forEach( (area) -> {
            allProperties.addAll(area.getRelationships());
        });
        
        return allProperties;
    }
    
    /**
     * Returns which area each concept is summarized by
     * @return 
     */
    public Map<Concept, Area> getConceptAreas() {
        Map<Concept, Area> conceptAreas = new HashMap<>();
        
        this.getAreas().forEach( (area) -> {
            area.getConcepts().forEach( (concept) -> {
                conceptAreas.put((Concept)concept, area);
            });
        });
        
        return conceptAreas;
    }
}
