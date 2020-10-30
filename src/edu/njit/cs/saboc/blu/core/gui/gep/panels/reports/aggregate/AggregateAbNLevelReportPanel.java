package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNLevelReportPanel;

/**
 *
 * @author Chris O
 */
public class AggregateAbNLevelReportPanel extends AbNLevelReportPanel {
    
    public AggregateAbNLevelReportPanel(PartitionedAbNConfiguration config) {
        super(config, new AggregateAbNLevelReportTableModel(config));
    }
}
