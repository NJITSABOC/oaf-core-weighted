package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.initializers;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.tan.BandTANGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.PartitionedAbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.framestate.FrameState;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public abstract class BandTANInitializer extends TANInitializer {
    
    public BandTANInitializer(AbNWarningManager warningManager, FrameState frameState) {
        super(warningManager, frameState);
    }
    
    @Override
    public TANInitializerType getInitializerType() {
        return TANInitializerType.BandTAN;
    }

    @Override
    public AbNPainter getAbNPainter(ClusterTribalAbstractionNetwork abn) {
        PartitionedAbNPainter painter = new PartitionedAbNPainter();
        painter.initialize(abn);
        
        return painter;
    }

    @Override
    public AbstractionNetworkGraph getGraph(JFrame parentFrame, TANConfiguration config, SinglyRootedNodeLabelCreator labelCreator) {
        return new BandTANGraph(config.getAbstractionNetwork(), labelCreator, config);
    }
}
