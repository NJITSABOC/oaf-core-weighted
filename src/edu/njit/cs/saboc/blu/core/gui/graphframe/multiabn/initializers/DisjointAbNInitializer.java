package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointAbNGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateDisjointAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateSinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.DisjointAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.GraphFrameInitializer;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.TaskBarPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.taskbarpanels.GenericAbNTaskBarPanel;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public abstract class DisjointAbNInitializer implements GraphFrameInitializer<DisjointAbstractionNetwork, DisjointAbNConfiguration> {

    @Override
    public AbstractionNetworkGraph getGraph(
            JFrame parentFrame, 
            DisjointAbNConfiguration config, 
            SinglyRootedNodeLabelCreator labelCreator) {
        
        return new DisjointAbNGraph(parentFrame, config.getAbstractionNetwork(), labelCreator);
    }

    @Override
    public TaskBarPanel getTaskBar(
            MultiAbNGraphFrame graphFrame, 
            DisjointAbNConfiguration config) {
        
        return new GenericAbNTaskBarPanel(graphFrame, config);
    }

    @Override
    public AbNPainter getAbNPainter(DisjointAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateDisjointAbNPainter();
        } else {
            return new DisjointAbNPainter();
        }
    }

    @Override
    public SinglyRootedNodeLabelCreator getLabelCreator(DisjointAbstractionNetwork abn) {
        if (abn.isAggregated()) {
            return new AggregateSinglyRootedNodeLabelCreator();
        } else {
            return new SinglyRootedNodeLabelCreator();
        }
    }
}
