
package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.initializer.AbNExplorationPanelGUIInitializer;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public interface GraphFrameInitializer<
        T extends AbstractionNetwork,
        V extends AbNConfiguration> {
    
    public AbstractionNetworkGraph getGraph(JFrame parentFrame, V config, SinglyRootedNodeLabelCreator labelCreator);

    public AbNConfiguration getConfiguration(T abn, AbNDisplayManager displayManager);

    public TaskBarPanel getTaskBar(MultiAbNGraphFrame graphFrame, V config);

    public AbNPainter getAbNPainter(T abn);

    public SinglyRootedNodeLabelCreator getLabelCreator(T abn);

    public AbNExplorationPanelGUIInitializer getExplorationGUIInitializer(V config);
}
