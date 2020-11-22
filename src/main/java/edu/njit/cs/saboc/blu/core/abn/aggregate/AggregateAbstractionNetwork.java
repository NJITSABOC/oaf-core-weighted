package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 * Represents an abstraction network that has been aggregated and 
 * consists of aggregate nodes.
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <ABN_T>
 */
public interface AggregateAbstractionNetwork<T extends AggregateNode,
        ABN_T extends AbstractionNetwork> {
    
    public ABN_T getNonAggregateSourceAbN();
    
    public int getAggregateBound();
    public boolean isWeightedAggregated();
    
    public ABN_T expandAggregateNode(T node);
    public AggregatedProperty getAggregatedProperty();
    
}
