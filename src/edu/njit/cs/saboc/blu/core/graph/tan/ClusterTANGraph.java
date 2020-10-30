package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;

/**
 *
 * @author Chris O
 */
public class ClusterTANGraph<T extends ClusterTribalAbstractionNetwork> extends AbstractionNetworkGraph<T> {
    
    public ClusterTANGraph(T tan, SinglyRootedNodeLabelCreator labelCreator, TANConfiguration config) {
        
        super(tan, labelCreator);
        
        super.setAbstractionNetworkLayout(new TANLayout(this, tan, config));
    }
}
