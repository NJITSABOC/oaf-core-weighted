package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 * The details of why a node was modified between the "FROM" and "TO" versions
 * of an abstraction network.
 * 
 * @author Chris O
 */
public class ModifiedNodeDetails extends NodeChangeDetails {
    
    public ModifiedNodeDetails(Node node, 
            Set<NodeConceptChange> conceptChanges, 
            Set<ChildOfChange> childOfChanges) {
        
        super(ChangeState.Modified, node, conceptChanges, childOfChanges);
    }
}