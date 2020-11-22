package edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.visitor.result.AncestorDepthResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A visitor for finding the longest path hierarchical depth for each node in
 * theHierarchy
 * 
 * @author Chris O
 */
public class HierarchyDepthVisitor<T> extends TopologicalVisitor<T> {
    
    private final Map<T, Integer> depth = new HashMap<>();
    
    private final ArrayList<AncestorDepthResult<T>> result = new ArrayList<>();
       
    public HierarchyDepthVisitor(Hierarchy<T> theHierarchy) {
        super(theHierarchy);
    }
    
    @Override
    public void visit(T node) {
        Hierarchy<T> theHierarchy = super.getHierarchy();
        
        Set<T> parents = theHierarchy.getParents(node);

        int maxParentDepth = -1;

        for (T parent : parents) {
            if (depth.containsKey(parent) && depth.get(parent) > maxParentDepth) {
                maxParentDepth = depth.get(parent);
            }
        }

        depth.put(node, maxParentDepth + 1);
        
        result.add(new AncestorDepthResult<>(node, depth.get(node)));
    }
    
    public ArrayList<AncestorDepthResult<T>> getResult() {
        return result;
    }
    
    public Map<T, Integer> getAllDepths() {
        return depth;
    }
}
