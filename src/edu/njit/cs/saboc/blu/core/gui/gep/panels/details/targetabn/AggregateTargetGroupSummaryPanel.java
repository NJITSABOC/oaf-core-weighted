package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateTargetGroupSummaryPanel extends TargetGroupSummaryPanel {
    
    public AggregateTargetGroupSummaryPanel(TargetAbNConfiguration configuration) {
        
        super(configuration, new TargetGroupSummaryTextFactory(configuration));
        
    }
}
