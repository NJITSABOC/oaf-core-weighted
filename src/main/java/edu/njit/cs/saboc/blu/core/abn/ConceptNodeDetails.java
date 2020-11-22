package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * Returns the set of nodes that summarize a given concept
 * 
 * @author Chris O
 * @param <T>
 */
public class ConceptNodeDetails<T extends Node> {
    
    private final Concept concept;
    private final Set<T> nodes;
    
    public ConceptNodeDetails(Concept concept, Set<T> nodes) {
        this.concept = concept;
        this.nodes = nodes;
    }

    public Concept getConcept() {
        return concept;
    }

    public Set<T> getNodes() {
        return nodes;
    }
}
