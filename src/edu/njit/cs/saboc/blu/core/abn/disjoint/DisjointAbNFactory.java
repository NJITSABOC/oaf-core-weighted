package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A factory interface for defining classes that create implementation-dependant 
 * disjoint abstraction network classes
 * 
 * @author Chris O
 * @param <PARENTNODE_T>
 * @param <DISJOINTNODE_T>
 */
public interface DisjointAbNFactory<PARENTNODE_T extends Node, DISJOINTNODE_T extends DisjointNode<PARENTNODE_T>> {
    
    public DISJOINTNODE_T createDisjointNode(Hierarchy<Concept> hierarchy, Set<PARENTNODE_T> overlaps);
    
}
