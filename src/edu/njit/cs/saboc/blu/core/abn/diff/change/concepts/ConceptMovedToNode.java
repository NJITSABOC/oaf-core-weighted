package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Represents a concept moving FROM the given node TO a different node between
 * the "FROM" and "TO" versions of an abstraction network.
 * 
 * @author Chris
 */
public class ConceptMovedToNode extends NodeConceptChange {
    
    private final Node to;
    
    public ConceptMovedToNode(Node from, Node to, Concept concept) {
        
        super(from, concept);
        
        this.to = to;
    }
    
    public Node getMovedTo() {
        return to;
    }
    
    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Moved to different <nodeTypeName>";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> moved to <nodeName> <nodeTypeName>.";

        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        str = str.replaceAll("<nodeName>", this.getMovedTo().getName());
        
        str = factory.format(str);
        
        return str;
    }
}
