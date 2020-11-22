package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.ClusterTANGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.AggregateTANPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan.TANPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels.PartitionedAbNTaskBarPanel;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public abstract class TANInitializer implements GraphFrameInitializer<ClusterTribalAbstractionNetwork, TANConfiguration> {

    public static enum TANInitializerType {
        ClusterTAN,
        BandTAN
    }
    
    private final AbNWarningManager warningManager;
    private final FrameState frameState;
    
    public TANInitializer(AbNWarningManager warningManager, FrameState frameState) {
        this.warningManager = warningManager;
        this.frameState = frameState;
    }
    
    
    public TANInitializerType getInitializerType() {
        return TANInitializerType.ClusterTAN;
    }
    
    @Override
    public AbstractionNetworkGraph getGraph(JFrame parentFrame, TANConfiguration config, SinglyRootedNodeLabelCreator labelCreator) {
        return new ClusterTANGraph(config.getAbstractionNetwork(), labelCreator, config);
    }

    @Override
    public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TANConfiguration config) {
        
        PartitionedAbNTaskBarPanel taskBar = new PartitionedAbNTaskBarPanel(graphFrame, config);
        
        return taskBar;
    }

    @Override
    public AbNPainter getAbNPainter(ClusterTribalAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateTANPainter();
        } else {
            return new TANPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(ClusterTribalAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateSinglyRootedNodeLabelCreator<>();
        } else {
            return new SinglyRootedNodeLabelCreator<>();
        }
    }

    @Override
    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(TANConfiguration config) {

        AggregationAction aggregationAction = (ap) -> {
                frameState.setAggregateProperty(ap);
                ClusterTribalAbstractionNetwork aggregateTAN = config.getAbstractionNetwork().getAggregated(ap);
                config.getUIConfiguration().getAbNDisplayManager().displayTribalAbstractionNetwork(aggregateTAN);     
        };
        
        AggregateableAbNExplorationPanelInitializer initializer = 
                new AggregateableAbNExplorationPanelInitializer(warningManager, frameState, aggregationAction) {

            @Override
            public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
                super.initializeAbNDisplayPanel(displayPanel, startUp);
            }
        };

        return initializer;
    }
}
