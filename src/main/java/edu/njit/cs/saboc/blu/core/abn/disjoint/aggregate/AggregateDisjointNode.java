package edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a disjoint node that summarizes a set of other disjoint nodes
 * that summarizer fewer than a selected number of concepts.
 * 
 * @author Chris O
 * @param <T>
 */
public class AggregateDisjointNode<T extends Node> extends DisjointNode<T> implements AggregateNode<DisjointNode<T>> {
    
    private final Hierarchy<DisjointNode<T>> aggregatedHierarchy;
    
    public AggregateDisjointNode(
            Hierarchy<Concept> conceptHierarchy, 
            Set<T> overlapsIn,
            Hierarchy<DisjointNode<T>> aggregatedHierarchy) {

        super(conceptHierarchy, overlapsIn);
        
        this.aggregatedHierarchy = aggregatedHierarchy;
    }

    @Override
    public Hierarchy<DisjointNode<T>> getAggregatedHierarchy() {
        return aggregatedHierarchy;
    }

    @Override
    public Set<DisjointNode<T>> getAggregatedNodes() {
        Set< DisjointNode<T>> aggregatedPAreas = new HashSet<>(aggregatedHierarchy.getNodes());
        aggregatedPAreas.remove(aggregatedHierarchy.getRoot());
        
        return aggregatedPAreas;
    }
}
