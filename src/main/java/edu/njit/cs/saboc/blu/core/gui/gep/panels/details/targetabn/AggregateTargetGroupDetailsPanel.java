package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateTargetGroupDetailsPanel extends NodeDetailsPanel<AggregateTargetGroup> {
    
    public AggregateTargetGroupDetailsPanel(TargetAbNConfiguration config) {
        
        super(new TargetGroupSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new NodeConceptList(config),
                config);
    }
}
