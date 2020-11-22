
package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.ArrayList;

/**
 * A visitor for obtaining a list of DAG nodes in topological order
 * 
 * @author Chris O
 * @param <T>
 */
public class TopologicalListVisitor<T> extends TopologicalVisitor<T> {
    
    private final ArrayList<T> result = new ArrayList<>();
    
    public TopologicalListVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    @Override
    public void visit(T element) {
        result.add(element);
    }
    
    public ArrayList<T> getTopologicalList() {
        return result;
    }
}
