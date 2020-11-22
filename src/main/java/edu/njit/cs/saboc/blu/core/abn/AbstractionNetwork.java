package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.CachedAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Chris O
 * 
 * Base class for all kinds of abstraction networks.
 * 
 * Abstraction networks are summaries of an ontology. An abstraction
 * network consists of a hierarchy of nodes that summarizes
 * a hierarchy of concepts.
 * 
 * @param <NODE_T> The type of node in the abstraction network
 */
public abstract class AbstractionNetwork<NODE_T extends Node> {

    private final Hierarchy<NODE_T> nodeHierarchy;
    private final Hierarchy<Concept> sourceHierarchy;
    
    private final AbNDerivation derivation;
    
    protected AbstractionNetwork(
            Hierarchy<NODE_T> nodeHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            AbNDerivation derivation) {
        
        this.nodeHierarchy = nodeHierarchy;
        this.sourceHierarchy = sourceHierarchy;
        this.derivation = derivation;
    }
    
    public AbNDerivation getDerivation() {
        return derivation;
    }
    
    public CachedAbNDerivation getCachedDerivation() {
        return new CachedAbNDerivation(this);
    }

    public int getNodeCount() {
        return nodeHierarchy.size();
    }
    
    public Hierarchy<Concept> getSourceHierarchy() {
        return sourceHierarchy;
    }
    
    public Set<NODE_T> getNodes() {
        return nodeHierarchy.getNodes();
    }
    
    public Hierarchy<NODE_T> getNodeHierarchy() {
        return nodeHierarchy;
    }
    
    /**
     * Returns the set of nodes that contain the given concept
     * @param concept
     * @return 
     */
    public Set<NODE_T> getNodesWith(Concept concept) {
        
        Set<NODE_T> nodes = nodeHierarchy.getNodes().stream().filter( 
                (node) -> {
                    return node.getConcepts().contains(concept); 
                }).collect(Collectors.toSet());
        
        return nodes;
    }
    
    /**
     * Returns the set of nodes that contain the given query (searches anywhere in node name).
     * 
     * Does not consider case when searching.
     * 
     * @param query
     * @return 
     */
    public Set<NODE_T> searchNodes(String query) {
        Set<NODE_T> nodes = nodeHierarchy.getNodes().stream().filter( 
                (node) -> {return node.getName().toLowerCase().contains(query.toLowerCase()); }).collect(Collectors.toSet());
        
        return nodes;
    }
    
    /**
     * A linear search through the abstraction network's nodes to find concepts.
     * The results will consists of concepts that contain the given query.
     * 
     * Does not consider case when searching.
     * 
     * @param query
     * @return 
     */
    public Set<ConceptNodeDetails<NODE_T>> searchConcepts(String query) {
        Set<ConceptNodeDetails<NODE_T>> result = new HashSet<>();
        
        Map<Concept, Set<NODE_T>> conceptNodes = new HashMap<>();
        
        nodeHierarchy.getNodes().forEach( (node) -> {
            Set<Concept> conceptResults = node.getConcepts().stream().filter( (concept) -> {
                return concept.getName().toLowerCase().contains(query.toLowerCase());
            }).collect(Collectors.toSet());
            
            conceptResults.forEach( (concept) -> {
               if(!conceptNodes.containsKey(concept)) {
                   conceptNodes.put(concept, new HashSet<>());
               }
               
               conceptNodes.get(concept).add(node);
            });
        });
        
        conceptNodes.forEach( (concept, nodes) -> {
            result.add(new ConceptNodeDetails<>(concept, nodes));
        });
        
        return result;
    }
        
    public abstract Set<ParentNodeDetails<NODE_T>> getParentNodeDetails(NODE_T node);
}
