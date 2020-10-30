package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNContainerReportPanel;

/**
 *
 * @author Chris O
 */

public class AggregateContainerReportPanel extends AbNContainerReportPanel {
    
    public AggregateContainerReportPanel(PartitionedAbNConfiguration config) {
        super(config, new AggregateContainerReportTableModel(config));
    }
}

