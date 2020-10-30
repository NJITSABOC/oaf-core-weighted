package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class ImportedConceptNodeReport {
    private final Set<Node> nodes;
    
    private final Concept concept;
    
    public ImportedConceptNodeReport(Concept concept, Set<Node> groups) {
        this.concept = concept;
        this.nodes = groups;
    }
    
    public Concept getConcept() {
        return concept;
    }
    
    public Set<Node> getNodes() {
        return nodes;
    }
}
