package edu.njit.cs.saboc.blu.core.abn.diff.change.childof;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;

/**
 * Represents a chance to the CHILD OF links between two 
 * nodes between the "FROM" version of an abstraction network and the 
 * "TO" version of an abstraction network.
 * 
 * @author Chris
 */
public class ChildOfChange implements AbNChange {
    
    private final Node child;
    private final Node parent;
    private final ChangeState state;
    
    public ChildOfChange(
            Node child, 
            Node parent, 
            ChangeState state) {
        
        this.child = child;
        this.parent = parent;
        this.state = state;
    }
    
    public Node getChildNode() {
        return child;
    }
    
    public Node getParentNode() {
        return parent;
    }
    
    public ChangeState getChangeState() {
        return state;
    }

    @Override
    public String getChangeName(AbNTextConfiguration config) {
        if(state.equals(ChangeState.Introduced)) {
            return "Child of introduced";
        } else {
            return "Child of removed";
        }
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        if(state.equals(ChangeState.Introduced)) {
            return String.format("Child of to %s introduced", parent.getName());
        } else {
            return String.format("Child of to %s removed", parent.getName());
        }
    }
}
