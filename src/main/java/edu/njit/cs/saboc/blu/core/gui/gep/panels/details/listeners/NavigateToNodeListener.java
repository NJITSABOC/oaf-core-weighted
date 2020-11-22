package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 * A listener for navigating to some kind of singly rooted node (e.g., partial-area) in the GEP
 * 
 * @author Chris O
 */
public class NavigateToNodeListener<T extends SinglyRootedNode> extends EntitySelectionAdapter<T> {

    private final AbNConfiguration config;
    
    public NavigateToNodeListener(AbNConfiguration config) {
        this.config = config;
    }
    
    @Override
    public void entityDoubleClicked(SinglyRootedNode group) {
        AbNDisplayPanel displayPanel = config.getUIConfiguration().getDisplayPanel();
        
        SinglyRootedNodeEntry entry = displayPanel.getGraph().getNodeEntries().get(group);
        displayPanel.getAutoScroller().autoNavigateToNodeEntry(entry);
    }
}
