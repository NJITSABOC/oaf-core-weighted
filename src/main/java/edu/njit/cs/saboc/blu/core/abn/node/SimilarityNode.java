package edu.njit.cs.saboc.blu.core.abn.node;

import java.util.Set;

/**
 * Represents an abstraction network node that captures similarity of some kind 
 * (e.g., all concepts in a partial-area taxonomy area have the same types 
 * of semantic relationships)
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class SimilarityNode<T extends SinglyRootedNode> extends PartitionedNode {
    
    public SimilarityNode(Set<T> internalNodes) {
        super(internalNodes);
    }
}
