package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 */
public class DisjointNodeDetailsPanel extends NodeDetailsPanel {

    public DisjointNodeDetailsPanel(DisjointAbNConfiguration config) {
        super(new DisjointNodeSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new NodeConceptList(config),
                config);
    }
}