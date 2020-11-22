package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 * An interface that represents a subset of an abstract network, based on 
 * the selection of a node within the original abstraction network. The
 * rooted sub abstraction network consists of the selected root and 
 * some, or all, of its descendant nodes.
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <ABN_T>
 */
public interface RootedSubAbstractionNetwork<T extends Node, ABN_T extends AbstractionNetwork> 
        extends SubAbstractionNetwork<ABN_T> {
    
    public T getSelectedRoot();
}
