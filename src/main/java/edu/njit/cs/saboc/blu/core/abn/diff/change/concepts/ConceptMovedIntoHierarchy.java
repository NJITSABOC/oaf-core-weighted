package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept that was previously not in the subhierarchy of concepts
 * summarized by an abstraction network in the "FROM" release being summarized
 * by a node in the "TO" release. 
 * 
 * Disjoint from the ConceptAddedToOntology change type.
 * 
 * @author Chris
 */
public class ConceptMovedIntoHierarchy extends NodeConceptChange {
    
    public ConceptMovedIntoHierarchy(Node node, Concept concept) {
        super(node, concept);
    }
    
    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Added to subhierarchy";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> introduced to ontology subhierarchy (it was previously in the ontology).";

        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        
        return str;
    }
}
