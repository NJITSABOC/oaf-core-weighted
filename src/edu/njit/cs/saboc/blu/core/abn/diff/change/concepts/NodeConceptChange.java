package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents changes to the set of nodes(s) that summarize a concept.
 * 
 * @author Chris
 */
public abstract class NodeConceptChange implements AbNChange {
    
    private final Node node;
    private final Concept concept;
        
    protected NodeConceptChange(
            Node node, 
            Concept concept) {
        
        this.node = node;
        this.concept = concept;
    }

    public Node getNode() {
        return node;
    }
    
    public Concept getConcept() {
        return concept;
    }
}
