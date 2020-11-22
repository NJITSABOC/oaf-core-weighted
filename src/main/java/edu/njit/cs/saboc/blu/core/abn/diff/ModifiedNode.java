package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ModifiedNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 * Represents a node that was modified in some way between the "FROM"
 * version of the abstraction network and the "TO" version of the 
 * abstraction network. 
 * 
 * For example, if the subhierarchy of concepts summarized by a node
 * changes between FROM and TO, then it would be a modified node.
 * 
 * @author Chris O
 */
public class ModifiedNode extends DiffNode<ModifiedNodeDetails> {

    private final Node from;
    private final Node to;
    
    public ModifiedNode(Node from, Node to, ModifiedNodeDetails details) {
        super(details);
        
        this.from = from;
        this.to = to;
    }
    
    public Node getFromNode() {
        return from;
    }
    
    public Node getToNode() {
        return to;
    }
}