package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;

/**
 *
 * @author Chris O
 */
public class DisjointNodeHierarchyPanel extends NodeHierarchyPanel {

    public DisjointNodeHierarchyPanel(DisjointAbNConfiguration configuration) {
        super(configuration, 
                new ParentDisjointNodeTableModel(configuration), 
                new ChildDisjointNodeTableModel(configuration));
    }
}
