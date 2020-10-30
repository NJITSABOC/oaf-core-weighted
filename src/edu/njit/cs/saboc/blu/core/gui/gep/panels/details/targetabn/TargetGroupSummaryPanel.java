package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TargetGroupSummaryPanel extends NodeSummaryPanel<TargetGroup> {

    private final TargetAbNConfiguration configuration;
    
    public TargetGroupSummaryPanel(
            TargetAbNConfiguration configuration, 
            TargetGroupSummaryTextFactory summaryTextFactory) {
        
        super(summaryTextFactory);

        this.configuration = configuration;
    }

    public TargetGroupSummaryPanel(TargetAbNConfiguration configuration) {
        this(configuration, new TargetGroupSummaryTextFactory(configuration));
    }
    
    @Override
    public void setContents(TargetGroup parea) {        
        super.setContents(parea);
                
    }
    
    @Override
    public void clearContents() {
        super.clearContents();
        
    }
}
