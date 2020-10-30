package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

/**
 * The various types of changes that affect how a concept is summarized 
 * between the FROM and TO versions of an abstraction network
 * 
 * @author Chris O
 */
public enum NodeConceptSetChangeType {
    // Concept added to node
    AddedToOnt,
    AddedToHierarchy,
    AddedToNode,
    MovedFromNode,
    
    // Concept removed from node
    RemovedFromOnt,
    RemovedFromHierarchy,
    RemovedFromNode,
    MovedToNode
}
