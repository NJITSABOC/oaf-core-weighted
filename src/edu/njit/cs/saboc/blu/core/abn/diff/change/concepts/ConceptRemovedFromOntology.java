package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept that was removed from an ontology and is no longer
 * summarized by the given node
 * 
 * @author Chris
 */
public class ConceptRemovedFromOntology extends NodeConceptChange {
    
    public ConceptRemovedFromOntology(Node node, Concept concept) {
        super(node, concept);
    }

    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Deleted from ontology";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> deleted from ontology.";

        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        
        return str;
    }
}
