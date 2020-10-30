package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;

/**
 *
 * @author Chris O
 */
public class BandTANGraph<T extends ClusterTribalAbstractionNetwork> extends AbstractionNetworkGraph<T> {
    public BandTANGraph(T tan, SinglyRootedNodeLabelCreator labelCreator, TANConfiguration config) {
        
        super(tan, labelCreator);
        
        AbstractionNetworkGraphLayout<T> layout = (AbstractionNetworkGraphLayout<T>)new BandTANLayout(
                        this, 
                        tan, 
                        config);
        
        super.setAbstractionNetworkLayout(layout);
    }
}
