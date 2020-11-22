package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 * The details of why a node was removed between the "FROM" and "TO" versions
 * of an abstraction network.
 * 
 * @author Chris O
 */
public class RemovedNodeDetails extends NodeChangeDetails {
    
    public RemovedNodeDetails(Node node, 
            Set<NodeConceptChange> conceptChanges, 
            Set<ChildOfChange> childOfChanges) {
        
        super(ChangeState.Removed, node, conceptChanges, childOfChanges);
    }
}
