package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A factory class for creating/obtaining implementation-specific target
 * abstraction network objects
 * 
 * @author Chris O
 */
public abstract class TargetAbstractionNetworkFactory {

    public TargetAbstractionNetworkFactory() {
        
    }
    
    public TargetAbstractionNetwork createTargetAbstractionNetwork(
            Hierarchy<TargetGroup> groupHierarchy, 
            Hierarchy<Concept> sourceHierarchy,
            TargetAbNDerivation derivation) {
        
        return new TargetAbstractionNetwork(
                this,
                groupHierarchy, 
                sourceHierarchy,
                derivation);
    }
    
    public TargetAbstractionNetworkFromPArea createTargetAbNFromPArea(
            TargetAbstractionNetwork targetAbN, 
            PAreaTaxonomy sourceTaxonomy, 
            PArea sourcePArea) {
        
        return new TargetAbstractionNetworkFromPArea(
                targetAbN, 
                sourceTaxonomy, 
                sourcePArea);
        
    }
    
    public abstract Set<RelationshipTriple> getRelationshipsToTargetHierarchyFor(
            Concept concept, 
            Set<InheritableProperty> relationshipTypes, 
            Hierarchy<Concept> targetHierarchy);
}
