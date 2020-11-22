package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.targetabn.TargetAbNGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.target.AggregateTargetAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.target.TargetAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels.GenericAbNTaskBarPanel;
import javax.swing.JFrame;

/**
 *
 * @author CHris O
 */
public abstract class TargetAbNInitializer implements GraphFrameInitializer<TargetAbstractionNetwork, TargetAbNConfiguration> {

    private final AbNWarningManager warningManager;
    private final FrameState frameState;

    public TargetAbNInitializer(AbNWarningManager warningManager, FrameState frameState) {
        this.warningManager = warningManager;
        this.frameState = frameState;
    }

    @Override
    public AbstractionNetworkGraph getGraph(
            JFrame parentFrame, 
            TargetAbNConfiguration config, 
            SinglyRootedNodeLabelCreator labelCreator) {
        
        return new TargetAbNGraph(parentFrame, config.getTargetAbstractionNetwork(), labelCreator);
    }

    @Override
    public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, TargetAbNConfiguration config) {
        return new GenericAbNTaskBarPanel(graphFrame, config);
    }

    @Override
    public AbNPainter getAbNPainter(TargetAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateTargetAbNPainter();
        } else {
            return new TargetAbNPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(TargetAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new SinglyRootedNodeLabelCreator<TargetGroup>() {

                @Override
                public String getCountStr(TargetGroup group) {

                    AggregateTargetGroup aGroup = (AggregateTargetGroup) group;

                    if (aGroup.getAggregatedNodes().size() > 1) {
                        int targetCount = group.getIncomingRelationshipTargets().size();
                        int sourceCount = group.getIncomingRelationshipSources().size();

                        return String.format("(%d) [%d] {%d}", targetCount, sourceCount, aGroup.getAggregatedNodes().size());
                    } else {
                        int targetCount = group.getIncomingRelationshipTargets().size();
                        int sourceCount = group.getIncomingRelationshipSources().size();

                        return String.format("(%d) [%d]", targetCount, sourceCount);
                    }
                }

            };

        } else {
            return new SinglyRootedNodeLabelCreator<TargetGroup>() {

                @Override
                public String getCountStr(TargetGroup group) {

                    int targetCount = group.getIncomingRelationshipTargets().size();
                    int sourceCount = group.getIncomingRelationshipSources().size();

                    return String.format("(%d) [%d]", targetCount, sourceCount);
                }
            };
        }

    }

    @Override
    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(TargetAbNConfiguration config) {
        AggregatationSliderPanel.AggregationAction aggregationAction = (ap) -> {
            frameState.setAggregateProperty(ap);
            TargetAbstractionNetwork aggregateAbN = config.getTargetAbstractionNetwork().getAggregated(ap);
            config.getUIConfiguration().getAbNDisplayManager().displayTargetAbstractionNetwork(aggregateAbN);
        };
        
        return new AggregateableAbNExplorationPanelInitializer(warningManager, frameState, aggregationAction);
    }
}
