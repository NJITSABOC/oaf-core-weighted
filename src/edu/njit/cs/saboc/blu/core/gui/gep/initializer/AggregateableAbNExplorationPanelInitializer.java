
package edu.njit.cs.saboc.blu.core.gui.gep.initializer;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;

/**
 *
 * @author Chris O
 */
public class AggregateableAbNExplorationPanelInitializer extends BaseAbNExplorationPanelInitializer {
    
    private final AggregationAction aggregationAction;
    private final FrameState frameState;
    
    public AggregateableAbNExplorationPanelInitializer(
            AbNWarningManager warningManager,
            FrameState frameState,
            AggregationAction aggregationAction) {
        
        super(warningManager);
        this.frameState = frameState;
        this.aggregationAction = aggregationAction;
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        super.initializeAbNDisplayPanel(displayPanel, startUp);
        
        AggregatationSliderPanel aggregationPanel = new AggregatationSliderPanel(displayPanel, frameState, aggregationAction);
        
        displayPanel.addWidget(aggregationPanel);
    }
}
