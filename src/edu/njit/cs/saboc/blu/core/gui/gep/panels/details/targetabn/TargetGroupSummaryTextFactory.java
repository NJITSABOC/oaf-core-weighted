package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TargetGroupSummaryTextFactory extends NodeSummaryTextFactory<TargetGroup> {

    public TargetGroupSummaryTextFactory(TargetAbNConfiguration config) {
        super(config);
    }
    
    @Override
    public TargetAbNConfiguration getConfiguration() {
        return (TargetAbNConfiguration)super.getConfiguration();
    }
}
