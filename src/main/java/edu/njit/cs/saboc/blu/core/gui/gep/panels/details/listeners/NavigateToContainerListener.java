package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerListener extends EntitySelectionAdapter<PartitionedNode> {

    private final AbNDisplayPanel displayPanel;

    public NavigateToContainerListener(AbNDisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    @Override
    public void entityDoubleClicked(PartitionedNode container) {
        PartitionedNodeEntry entry = displayPanel.getGraph().getContainerEntries().get(container);
        
        displayPanel.getAutoScroller().autoNavigateToNodeEntry(entry);
    }
}
