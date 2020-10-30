package edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A factory class for creating aggregated disjoint abstraction networks. 
 * 
 * @author Chris O
 * @param <T>
 */
public class AggregateDisjointAbNFactory<T extends Node> implements AggregateAbNFactory<DisjointNode<T>, AggregateDisjointNode<T>> {

    @Override
    public AggregateDisjointNode createAggregateNode(Hierarchy<DisjointNode<T>> aggregatedNodes, Hierarchy<Concept> sourceHierarchy) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        aggregatedNodes.getNodes().forEach( (parea) -> {           
            hierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        Set<Concept> conceptsInPArea = hierarchy.getNodes();
        
        aggregatedNodes.getNodes().forEach( (parea) -> {
            Concept root = parea.getRoot();
            
            sourceHierarchy.getParents(root).forEach( (parent) -> {
               if(conceptsInPArea.contains(parent)) {
                   hierarchy.addEdge(root, parent);
               } 
            });
        });
        
        return new AggregateDisjointNode(hierarchy, aggregatedNodes.getRoot().getOverlaps(), aggregatedNodes);
    }
}
