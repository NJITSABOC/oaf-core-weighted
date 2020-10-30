package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * A base class for all visitors that are executed during a topological 
 * traversal of theHierarchy
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class TopologicalVisitor<T> extends HierarchyVisitor<T> {
    
    public TopologicalVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
}
