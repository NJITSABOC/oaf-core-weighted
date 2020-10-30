package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.TopologicalVisitor;
import java.util.HashSet;
import java.util.Set;

/**
 * A visitor for extracting the subhierarchy of disjoint nodes that summarize concepts 
 * that overlaps between a given subset of singly rooted nodes or a subset of the 
 * singly rooted nodes.
 * 
 * @author Chris O
 * @param <T>
 */
public class SubsetDisjointAbNSubhierarchyVisitor<T extends SinglyRootedNode> extends TopologicalVisitor<DisjointNode<T>>{
    
    private final Set<T> overlaps;
    
    private final Hierarchy<DisjointNode<T>> resultSubhierarchy;
    
    public SubsetDisjointAbNSubhierarchyVisitor(Hierarchy<DisjointNode<T>> theHierarchy, Set<T> overlaps) {
        super(theHierarchy);
        
        this.overlaps = overlaps;
        
        Set<DisjointNode<T>> resultRoots = new HashSet<>();
        
        theHierarchy.getRoots().forEach( (root) -> {
            if(overlaps.containsAll(root.getOverlaps())) {
                resultRoots.add(root);
            }
        });
        
        this.resultSubhierarchy = new Hierarchy<>(resultRoots);
    }

    @Override
    public void visit(DisjointNode<T> node) {
        
        if(SetUtilities.getSetDifference(node.getOverlaps(), overlaps).isEmpty()) {
            Hierarchy<DisjointNode<T>> theHierarchy = super.getHierarchy();

            Set<DisjointNode<T>> parents = theHierarchy.getParents(node);
            
            parents.forEach( (parent) -> {
                resultSubhierarchy.addEdge(node, parent);
            });
        }
    }
    
    public Hierarchy<DisjointNode<T>> getSubsetSubhierarchy() {
        return resultSubhierarchy;
    }
}
