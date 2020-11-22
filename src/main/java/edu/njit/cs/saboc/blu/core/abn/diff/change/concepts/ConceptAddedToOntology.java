package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a newly-created concept being summarized by a given node
 * in the "TO" version of an abstraction network
 * 
 * @author Chris
 */
public class ConceptAddedToOntology extends NodeConceptChange {

    public ConceptAddedToOntology(Node node, Concept concept) {
        super(node, concept);
    }
    
    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Introduced to ontology";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> introduced to ontology.";

        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        
        return str;
    }
}
