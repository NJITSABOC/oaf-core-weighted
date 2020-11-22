package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Set;

/**
 * A node that that summarizes a subhierarchy of other nodes that 
 * are smaller than some bound
 * 
 * @author Chris O
 * 
 * @param <T> The types of nodes that are aggregated
 */
public interface AggregateNode<T extends Node> {
    public Hierarchy<T> getAggregatedHierarchy();
    public Set<T> getAggregatedNodes();
}
