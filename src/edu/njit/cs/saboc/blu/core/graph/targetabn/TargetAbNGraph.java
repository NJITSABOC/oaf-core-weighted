package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 *
 * @author Chris O
 */
public class TargetAbNGraph<T extends TargetAbstractionNetwork> extends AbstractionNetworkGraph<T> {
    
    public TargetAbNGraph(
            final JFrame parentFrame, 
            final T targetAbN, 
            final SinglyRootedNodeLabelCreator labelCreator) {

        super(targetAbN, labelCreator);
        
        super.setAbstractionNetworkLayout(new TargetAbNLayout(this, targetAbN));
    }
}
