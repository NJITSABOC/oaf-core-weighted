package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept that was previously in the subhierarchy of concepts
 * summarized by an abstraction network in the "FROM" but no longer being summarized
 * by a node in the "TO" version of the abstraction network. 
 * 
 * Disjoint from the ConceptRemovedFromOntology class.
 * 
 * @author Chris
 */
public class ConceptMovedOutOfHierarchy extends NodeConceptChange {
    
    public ConceptMovedOutOfHierarchy(Node node, Concept concept) {
        super(node, concept);
    }
    
    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Removed from subhierarchy";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> removed from ontology subhierarchy (it is still in the ontology).";

        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        
        return str;
    }
}
