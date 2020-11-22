package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept moving FROM one node TO another node between the
 * "FROM" and "TO" versions of the abstraction network.
 * 
 * @author Chris
 */
public class ConceptMovedFromNode extends NodeConceptChange {
    
    private final Node from;
    
    public ConceptMovedFromNode(Node to, Node from, Concept concept) {
        super(to, concept);
        
        this.from = from;
    }
    
    public Node getMovedFrom() {
        return from;
    }
    
    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Moved from <nodeTypeName>";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> moved from <nodeName> <nodeTypeName>.";

        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        str = str.replaceAll("<nodeName>", this.getMovedFrom().getName());
        
        str = factory.format(str);
        
        return str;
    }
}
