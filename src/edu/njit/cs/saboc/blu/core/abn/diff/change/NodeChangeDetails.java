package edu.njit.cs.saboc.blu.core.abn.diff.change;

import edu.njit.cs.saboc.blu.core.abn.diff.change.childof.ChildOfChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.concepts.NodeConceptChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.util.Set;

/**
 * The details of how a node changed between the FROM and TO versions of
 * an abstraction network, if any changes exist.
 * 
 * @author Chris O
 */
public abstract class NodeChangeDetails {
    
    private final Node changedNode;
    
    private final ChangeState changeState;
    
    private final Set<NodeConceptChange> conceptChanges;
    
    private final Set<ChildOfChange> childOfChanges;
    
    protected NodeChangeDetails(
            ChangeState changeState, 
            Node changedNode, 
            Set<NodeConceptChange> conceptChanges,
            Set<ChildOfChange> childOfChanges) {
        
        this.changeState = changeState;
        this.changedNode = changedNode;
        this.conceptChanges = conceptChanges;
        this.childOfChanges = childOfChanges;
    }
    
    public ChangeState getNodeState() {
        return changeState;
    }
    
    public Node getChangedNode() {
        return changedNode;
    }
    
    public Set<NodeConceptChange> getConceptChanges() {
        return conceptChanges;
    }
    
    public Set<ChildOfChange> getChildOfChanges() {
        return childOfChanges;
    }
}