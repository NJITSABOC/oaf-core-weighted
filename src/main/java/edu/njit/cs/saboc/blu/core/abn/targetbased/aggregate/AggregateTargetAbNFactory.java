package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.IncomingRelationshipDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.RelationshipTriple;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 * A factory class for creating aggregate target abstraction networks
 *  
 * @author Chris O
 */
public class AggregateTargetAbNFactory implements AggregateAbNFactory<TargetGroup, AggregateTargetGroup> {

    @Override
    public AggregateTargetGroup createAggregateNode(Hierarchy<TargetGroup> aggregatedNodes, 
            Hierarchy<Concept> sourceHierarchy) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        Set<RelationshipTriple> allRelationshipTriples = new HashSet<>();
        
        aggregatedNodes.getNodes().forEach((group) -> {
            hierarchy.addAllHierarchicalRelationships(group.getHierarchy());
            
            allRelationshipTriples.addAll(group.getIncomingRelationshipDetails().getAllRelationships());
        });
        
        return new AggregateTargetGroup(hierarchy, 
                new IncomingRelationshipDetails(allRelationshipTriples), aggregatedNodes);
    }
}