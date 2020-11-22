package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * An interface for defining a factory class that creates aggregate nodes and other
 * implementation-dependant aggregate abn generation classes (if needed). 
 * 
 * Used as input for AggregateAbNGenerator
 * 
 * @author Chris O
 * 
 * @param <NODE_T>
 * @param <AGGREGATENODE_T>
 */
public interface AggregateAbNFactory<NODE_T extends Node, AGGREGATENODE_T extends Node & AggregateNode<NODE_T>> {
    
    public AGGREGATENODE_T createAggregateNode(
            Hierarchy<NODE_T> aggregatedNodes,
            Hierarchy<Concept> sourceHierarchy
    );
}
