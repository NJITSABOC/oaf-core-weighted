package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AggregateableAbNExplorationPanelInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.AggregatationSliderPanel.AggregationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.MinimapPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy.AggregatePAreaTaxonomyPainter;
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
public abstract class PAreaTaxonomyInitializer implements GraphFrameInitializer<PAreaTaxonomy, PAreaTaxonomyConfiguration> {
    
    public static enum PAreaInitializerType {
        PAreaTaxonomy,
        AreaTaxonomy
    }
    
    private final AbNWarningManager warningManager;
    private final FrameState frameState;
    
    public PAreaTaxonomyInitializer(AbNWarningManager warningManager, FrameState frameState) {
        this.warningManager = warningManager;
        this.frameState = frameState;
    }
    
    public PAreaInitializerType getInitializerType() {
        return PAreaInitializerType.PAreaTaxonomy;
    }

    @Override
    public AbstractionNetworkGraph getGraph(
            JFrame parentFrame, 
            PAreaTaxonomyConfiguration config, 
            SinglyRootedNodeLabelCreator labelCreator) {
        
        return new PAreaTaxonomyGraph(parentFrame, config.getPAreaTaxonomy(), labelCreator, config);
    }

    @Override
    public TaskBarPanel getTaskBar(
            MultiAbNGraphFrame graphFrame, 
            PAreaTaxonomyConfiguration config) {
        
        PartitionedAbNTaskBarPanel taskBar = new PartitionedAbNTaskBarPanel(graphFrame, config);

        return taskBar;
    }

    @Override
    public AbNPainter getAbNPainter(PAreaTaxonomy abn) {
        if (abn.isAggregated()) {
            return new AggregatePAreaTaxonomyPainter();
        } else {
            return new AbNPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(PAreaTaxonomy abn) {
        if (abn.isAggregated()) {
            return new AggregateSinglyRootedNodeLabelCreator<>();
        } else {
            return new SinglyRootedNodeLabelCreator<>();
        }
    }

    @Override
    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(PAreaTaxonomyConfiguration config) {

        AggregationAction aggregationAction = (ap) -> {
                frameState.setAggregateProperty(ap);
                PAreaTaxonomy aggregateTaxonomy = config.getPAreaTaxonomy().getAggregated(frameState.getAggregateProperty());            
                config.getUIConfiguration().getAbNDisplayManager().displayPAreaTaxonomy(aggregateTaxonomy);           
        };

        AggregateableAbNExplorationPanelInitializer initializer = 
                new AggregateableAbNExplorationPanelInitializer(warningManager, frameState, aggregationAction) {

            @Override
            public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
                super.initializeAbNDisplayPanel(displayPanel, startUp);

                MinimapPanel minimapPanel = new MinimapPanel(displayPanel);

                if (displayPanel.getGraph().getWidth() > displayPanel.getWidth() * 2
                        || displayPanel.getGraph().getHeight() > displayPanel.getHeight() * 2) {

                    displayPanel.addWidget(minimapPanel);
                }
            }
        };

        return initializer;
    }
}
