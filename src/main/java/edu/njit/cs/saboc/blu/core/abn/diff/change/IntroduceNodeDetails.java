package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 * The details of why a node was introduced in the TO version of 
 * an abstraction network.
 * 
 * @author Chris O
 */
public class IntroduceNodeDetails extends NodeChangeDetails {
    
    public IntroduceNodeDetails(Node node,
            Set<NodeConceptChange> conceptChanges, 
            Set<ChildOfChange> childOfChanges) {
        
        super(ChangeState.Introduced, node, conceptChanges, childOfChanges);
    }
}
