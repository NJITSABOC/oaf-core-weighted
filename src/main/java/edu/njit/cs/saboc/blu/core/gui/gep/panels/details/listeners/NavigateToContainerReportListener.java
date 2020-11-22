package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners;

import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class NavigateToContainerReportListener extends EntitySelectionAdapter<ContainerReport> {

    private final AbNConfiguration config;

    public NavigateToContainerReportListener(AbNConfiguration config) {
        this.config = config;
    }

    @Override
    public void entityDoubleClicked(ContainerReport containerReport) {
        AbNDisplayPanel displayPanel = config.getUIConfiguration().getDisplayPanel();
        
        PartitionedNodeEntry entry = displayPanel.getGraph().getContainerEntries().get(containerReport.getContainer());
        
        displayPanel.getAutoScroller().autoNavigateToNodeEntry(entry);
    }
}
