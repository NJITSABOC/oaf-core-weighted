package edu.njit.cs.saboc.blu.core.abn.diff.change.concepts;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * Represents a concept no longer being summarized by a node in the "TO" version
 * of the abstraction network but it is still summarized by at least one node
 * it was summarized by in the "FROM" release.
 * 
 * @author Chris O
 */
public class ConceptRemovedFromNode extends NodeConceptChange {

    private final Set<Node> remainsInNodes;
    
    public ConceptRemovedFromNode(Node node, Concept concept, Set<Node> remainsInNodes) {
        super(node, concept);
        
        this.remainsInNodes = remainsInNodes;
    }
    
    public Set<Node> getOtherNodes() {
        return remainsInNodes;
    }

    @Override
    public String getChangeName(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "Removed from <nodeTypeName>";

        str = factory.format(str);
        
        return str;
    }

    @Override
    public String getChangeDescription(AbNTextConfiguration config) {
        AbNTextFormatter factory = new AbNTextFormatter(config);
        
        String str = "<conceptName> was removed from <diffNodeName> <nodeTypeName>, "
                + "it is still in <nodeCount> other <nodeTypeName count=<nodeCount>>.";
        
        str = str.replaceAll("<conceptName>", super.getConcept().getName());
        str = str.replaceAll("<diffNodeName>", super.getNode().getName());
        str = str.replaceAll("<nodeCount>", Integer.toString(remainsInNodes.size()));
        
        str = factory.format(str);
        
        return str;
    }
    
    
}