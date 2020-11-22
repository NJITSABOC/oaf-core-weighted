
package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Edge;

/**
 * Represents a CHILD OF edge between two diff nodes. The edge can be
 * added, removed, or modified.
 * 
 * @author Chris O
 */
public class DiffEdge extends Edge<DiffNode> {
    
    public static enum EdgeState {
        Added,
        Removed,
        Unmodified
    }
    
    private final EdgeState state;
    
    public DiffEdge(DiffNode from, DiffNode to, EdgeState state) {
        super(from, to);
        
        this.state = state;
    }
    
    public DiffEdge(Edge<DiffNode> edge, EdgeState state) {
        this(edge.getSource(), edge.getTarget(), state);
    }
    
    public EdgeState getState() {
        return state;
    }
}
