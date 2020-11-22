package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.NodeChangeDetails;

/**
 * A class representing a diff node that summarizes  structural differences 
 * for a subhierarchy of concepts between two ontology releases. 
 * 
 * @author Chris O
 * 
 * @param <T>
 */
public class DiffNode<T extends NodeChangeDetails> {
    
    private final T changeDetails;
    
    public DiffNode(T changeDetails) {
        this.changeDetails = changeDetails;
    }
    
    public T getChangeDetails() {
        return changeDetails;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof DiffNode) {
            DiffNode other = (DiffNode)o;
            
            return other.getChangeDetails().getChangedNode().equals(this.getChangeDetails().getChangedNode()) &&
                    other.getChangeDetails().getNodeState().equals(this.getChangeDetails().getNodeState());
        } 
        
        return false;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(getChangeDetails().getChangedNode().hashCode() + getChangeDetails().getNodeState().hashCode());
    }
}
