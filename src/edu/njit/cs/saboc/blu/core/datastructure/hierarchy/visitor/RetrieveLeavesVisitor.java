package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.HashSet;
import java.util.Set;

/**
 * A visitor for obtaining all of the leaf nodes in the DAG
 * 
 * @author Chris O
 */
public class RetrieveLeavesVisitor<T> extends HierarchyVisitor<T> {
    private final Set<T> leaves = new HashSet<>();
    
    public RetrieveLeavesVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    @Override
    public void visit(T node) {
        Hierarchy<T> theHierarchy = super.getHierarchy();
        
        if(theHierarchy.getChildren(node).isEmpty()) {
            leaves.add(node);
        }
    }
    
    public Set<T> getLeaves() {
        return leaves;
    }
}