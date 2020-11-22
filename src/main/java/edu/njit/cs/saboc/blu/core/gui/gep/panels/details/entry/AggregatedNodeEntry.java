package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregatedNodeEntry<T extends Node> {
    
    private final T aggregatedNode;
    
    private final Set<AggregateNode<T>> aggregatedInto;
    
    public AggregatedNodeEntry(T aggregatedNode, Set<AggregateNode<T>> aggregatedInto) {
        this.aggregatedNode = aggregatedNode;
        this.aggregatedInto = aggregatedInto;
    }
    
    public T getAggregatedNode() {
        return aggregatedNode;
    }
    
    public Set<AggregateNode<T>> getAggregatedIntoNodes() {
        return aggregatedInto;
    }
}
