package edu.njit.cs.saboc.blu.core.abn.node;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a multi-rooted node that can be partitioned into one or more singly rooted nodes
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class PartitionedNode<T extends SinglyRootedNode> extends MultiRootedNode {
    
    private final Set<T> internalNodes;
    private final Map<Concept, Set<T>> conceptNodes;
    
    private static <T extends SinglyRootedNode> Hierarchy<Concept> createInternalHierarchy(Set<T> nodes) {
        Set<Concept> roots = nodes.stream().map( (node) -> node.getRoot()).collect(Collectors.toSet());

        Hierarchy<Concept> hierarchy = new Hierarchy<>(roots);
        
        nodes.forEach( (node) -> {
            hierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });
        
        return hierarchy;
    }
    
    public PartitionedNode(Set<T> internalNodes) {
        super(PartitionedNode.createInternalHierarchy(internalNodes));
        
        this.internalNodes = internalNodes;
        
        this.conceptNodes = new HashMap<>();
        
        this.internalNodes.forEach( (node) -> {
            Set<Concept> nodeConcepts = node.getConcepts();

            for (Concept concept : nodeConcepts) {
                if (!conceptNodes.containsKey(concept)) {
                    conceptNodes.put(concept, new HashSet<>());
                }
                
                conceptNodes.get(concept).add(node);
            }
        });
    }
    
    public Set<T> getInternalNodes() {
        return internalNodes;
    }
    
    @Override
    public Set<Concept> getConcepts() {
        return conceptNodes.keySet();
    }
    
    @Override
    public int getConceptCount() {
        return conceptNodes.keySet().size();
    }
    
    public Map<Concept, Set<T>> getConceptNodes() {
        return conceptNodes;
    }
    
    public boolean hasOverlappingConcepts() {
        Set<Concept> processedConcepts = new HashSet<>();
        
        Set<T> nodes = this.getInternalNodes();
        
        for(T node : nodes) {
            Set<Concept> pareaConcepts = node.getConcepts();
            
            for(Concept concept : pareaConcepts) {
                if(processedConcepts.contains(concept)) {
                    return true;
                } else {
                    processedConcepts.add(concept);
                }
            }
        }
        
        return false;
    }
    
    public Set<Concept> getOverlappingConcepts() {
        Set<OverlappingConceptDetails<T>> details = this.getOverlappingConceptDetails();
        
        Set<Concept> overlappingConcepts = new HashSet<>();
        
        details.forEach( (detail) -> {
            overlappingConcepts.add(detail.getConcept());
        });
        
        return overlappingConcepts;
    }
        
    public Set<OverlappingConceptDetails<T>> getOverlappingConceptDetails() {
        
        Set<OverlappingConceptDetails<T>> overlappingResults = new HashSet<>();
        
        getConceptNodes().forEach( (c, overlappingNodes) -> {
            if(overlappingNodes.size() > 1) {
                overlappingResults.add(new OverlappingConceptDetails(c, overlappingNodes));
            }
        });
        
        return overlappingResults;
    }
    
    public abstract String getName(String separator);
}
