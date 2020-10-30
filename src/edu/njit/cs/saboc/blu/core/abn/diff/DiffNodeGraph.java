
package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffEdge.EdgeState;
import static edu.njit.cs.saboc.blu.core.abn.diff.DiffEdge.EdgeState.Added;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Edge;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Graph;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a graph of Diff Nodes.
 * 
 * A specialized class based on, but does not (currently) extend, the Graph class.
 * 
 * @author Chris O
 */
public class DiffNodeGraph {

    private final Graph<DiffNode> addedEdges = new Graph<>();
    private final Graph<DiffNode> removedEdges = new Graph<>();;
    private final Graph<DiffNode> unchangedEdges = new Graph<>();
     
    public void addNode(DiffNode node) {
        addedEdges.addNode(node);
        removedEdges.addNode(node);
        unchangedEdges.addNode(node);
    }
    
    public void addEdge(DiffNode from, DiffNode to, EdgeState state) {
        addNode(from);
        addNode(to);
        
        switch(state) {
            case Added:
                addedEdges.addEdge(from, to);
                break;
                
            case Removed:
                removedEdges.addEdge(from, to);
                break;
                
            case Unmodified:
                unchangedEdges.addEdge(from, to);
                break;
        }
    }
    
    public Set<DiffNode> getOutgoingEdges(DiffNode node) {
        Set<DiffNode> outgoingEdges = new HashSet<>();
        
        outgoingEdges.addAll(addedEdges.getOutgoingEdges(node));
        outgoingEdges.addAll(removedEdges.getOutgoingEdges(node));
        outgoingEdges.addAll(unchangedEdges.getOutgoingEdges(node));
        
        return outgoingEdges;
    }
    
    public Set<DiffNode> getOutgoingEdges(DiffNode node, EdgeState state) {
        if(state.equals(EdgeState.Added)) {
            return addedEdges.getOutgoingEdges(node);
        } else if(state.equals(EdgeState.Removed)) {
            return removedEdges.getOutgoingEdges(node);
        } else {
            return unchangedEdges.getOutgoingEdges(node);
        }
    }
    
    public Set<DiffNode> getIncomingEdges(DiffNode node) {
        Set<DiffNode> incomingEdges = new HashSet<>();

        incomingEdges.addAll(addedEdges.getIncomingEdges(node));
        incomingEdges.addAll(removedEdges.getIncomingEdges(node));
        incomingEdges.addAll(unchangedEdges.getIncomingEdges(node));

        return incomingEdges;
    }
    
    public Set<DiffNode> getIncomingEdges(DiffNode node, EdgeState state) {
        
        if(state.equals(EdgeState.Added)) {
            return addedEdges.getIncomingEdges(node);
        } else if(state.equals(EdgeState.Removed)) {
            return removedEdges.getIncomingEdges(node);
        } else {
            return unchangedEdges.getIncomingEdges(node);
        }
    }
    
    public Set<DiffNode> getNodes() {
        return addedEdges.getNodes();
    }
    
    private Set<DiffEdge> toDiffEdges(Set<Edge<DiffNode>> edges, EdgeState state) {
        Set<DiffEdge> diffEdges = new HashSet<>();
        
        edges.forEach( (edge) -> {
            diffEdges.add(new DiffEdge(edge, state));
        });
        
        return diffEdges;
    }
    
    public Set<DiffEdge> getEdges() {
        Set<DiffEdge> allDiffEdges = new HashSet<>();
        
        allDiffEdges.addAll(toDiffEdges(addedEdges.getEdges(), EdgeState.Added));
        allDiffEdges.addAll(toDiffEdges(removedEdges.getEdges(), EdgeState.Removed));
        allDiffEdges.addAll(toDiffEdges(unchangedEdges.getEdges(), EdgeState.Unmodified));
        
        return allDiffEdges;
    }

    public Set<DiffEdge> getEdges(EdgeState state) {
        if (state.equals(EdgeState.Added)) {
            return toDiffEdges(addedEdges.getEdges(), EdgeState.Added);
        } else if (state.equals(EdgeState.Removed)) {
            return toDiffEdges(removedEdges.getEdges(), EdgeState.Removed);
        } else {
            return toDiffEdges(unchangedEdges.getEdges(), EdgeState.Unmodified);
        }
    }
}
