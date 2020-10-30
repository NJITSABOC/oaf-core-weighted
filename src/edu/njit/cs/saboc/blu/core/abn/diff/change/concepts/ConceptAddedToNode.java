package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a concept now being summarized by a node in the "TO" version
 * of an abstraction network while also still being summarized by at least one
 * node is summarized by in the "FROM" release
 * 
 * @author Chris O
 */
public class ConceptAddedToNode extends NodeConceptChange {

    private final Set<Node> otherCurrentNodes;
    
    public ConceptAddedToNode(Node node, Concept concept, Set<Node> otherCurrentNodes) {
        super(node, concept);
        
        this.otherCurrentNodes = otherCurrentNodes;
    }
    
    public Set<Node> getOtherNodes() {
        return otherCurrentNodes;
    }

    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Added to <nodeTypeName>";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> was added to <diffNodeName> <nodeTypeName>, "
                + "while still in <nodeCount> other <nodeTypeName count=<nodeCount>>.";
        
        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        str = str.replaceAll("<diffNodeName>", super.getNode().getName());
        str = str.replaceAll("<nodeCount>", Integer.toString(otherCurrentNodes.size()));
        
        str = factory.format(str);
        
        return str;
    }
    
    
}
