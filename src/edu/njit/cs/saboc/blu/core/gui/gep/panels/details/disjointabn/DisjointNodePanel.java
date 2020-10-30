package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;

/**
 *
 * @author Chris O
 */
public class DisjointNodePanel extends SinglyRootedNodePanel {
    
    public DisjointNodePanel(
            DisjointAbNConfiguration configuration) {
        
        super(new DisjointNodeDetailsPanel(configuration), 
                new DisjointNodeHierarchyPanel(configuration), 
                new ConceptHierarchyPanel(configuration),
                configuration);
    }
}
