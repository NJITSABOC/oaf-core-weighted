package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffEdge.EdgeState;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Directed Acyclic Graph (DAG) of Diff Nodes.
 * 
 * Based on, but does not currently extend, the Hierarchy class.
 * 
 * @author Chris O
 */
public class DiffNodeHierarchy {

    private final Set<DiffNode> addedRoots;
    private final Set<DiffNode> removedRoots;
    private final Set<DiffNode> transferredRoots;
    
    private final DiffNodeGraph diffGraph = new DiffNodeGraph();
    
    public DiffNodeHierarchy(
            Set<DiffNode> removedRoots, 
            Set<DiffNode> introducedRoots, 
            Set<DiffNode> transferredRoots) {
        
        this.removedRoots = removedRoots;
        this.addedRoots = introducedRoots;
        this.transferredRoots = transferredRoots;
    }
    
    public void addEdge(DiffNode from, DiffNode to, EdgeState edgeState) {
        diffGraph.addEdge(from, to, edgeState);
    }
    
    public Set<DiffNode> getRoots() {
        Set<DiffNode> roots = new HashSet<>();
        
        roots.addAll(addedRoots);
        roots.addAll(removedRoots);
        roots.addAll(transferredRoots);
        
        return roots;
    }
    
    public Set<DiffNode> getAddedRoots() {
        return addedRoots;
    }
    
    public Set<DiffNode> getRemovedRoots() {
        return removedRoots;
    }
    
    public Set<DiffNode> getTransferredRoots() {
        return transferredRoots;
    }
    
    public Set<DiffNode> getParents(DiffNode node) {
        return diffGraph.getOutgoingEdges(node);
    }
    
    public Set<DiffNode> getChildren(DiffNode node) {
        return diffGraph.getIncomingEdges(node);
    }
    
    public Set<DiffNode> getParents(DiffNode node, EdgeState state) {
        return diffGraph.getOutgoingEdges(node, state);
    }
    
    public Set<DiffNode> getChildren(DiffNode node, EdgeState state) {
        return diffGraph.getIncomingEdges(node, state);
    }
    
    public Set<DiffEdge> getEdges() {
        return diffGraph.getEdges();
    }
}
