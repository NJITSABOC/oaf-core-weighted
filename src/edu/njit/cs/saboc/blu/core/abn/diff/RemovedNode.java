package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.RemovedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 * Represents a node that existed in the "FROM" version of the abstraction 
 * network but does not exist in the "TO" version of the abstraction network.
 * 
 * @author Chris O
 */
public class RemovedNode extends DiffNode<RemovedNodeDetails> {
    
    private final Node removedNode;
    
    public RemovedNode(Node removedNode, RemovedNodeDetails details) {
        super(details);
        
        this.removedNode = removedNode;
    }
        
    public Node getRemovedNode() {
        return removedNode;
    }
}