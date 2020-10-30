package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.UnmodifiedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 * Represents a node that is identical in the "FROM" and "TO" versions of 
 * the abstraction network.
 * 
 * @author Chris O
 */
public class UnmodifiedNode extends DiffNode<UnmodifiedNodeDetails>{
    private final Node unmodifiedNode;
    
    public UnmodifiedNode(Node unmodifiedNode, UnmodifiedNodeDetails details) {
        super(details);
        
        this.unmodifiedNode = unmodifiedNode;
    }

    public Node getUnmodifiedNode() {
        return unmodifiedNode;
    }
}