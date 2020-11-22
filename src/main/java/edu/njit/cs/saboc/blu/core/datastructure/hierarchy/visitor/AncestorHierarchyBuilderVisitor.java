package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Set;

/**
 * A visitor for building an ancestor hierarchy for a specified set of starting
 * points
 * 
 * @author Chris O
 * @param <T>
 */
public class AncestorHierarchyBuilderVisitor<T> extends HierarchyVisitor<T> {
    
    private final Hierarchy<T> ancestorHierarchy;
    
    public AncestorHierarchyBuilderVisitor(
            Hierarchy<T> theHierarchy, 
            Hierarchy<T> ancestorHierarchy) {
        
        super(theHierarchy);
        
        this.ancestorHierarchy = ancestorHierarchy;
    }
    
    @Override
    public void visit(T node) {
        Hierarchy<T> theHierarchy = super.getHierarchy();
        
        Set<T> nodeParents = theHierarchy.getParents(node);

        nodeParents.forEach((T parent) -> {
            ancestorHierarchy.addEdge(node, parent);
        });
    }
    
    public Hierarchy<T> getAncestorHierarchy() {
        return ancestorHierarchy;
    }
}
