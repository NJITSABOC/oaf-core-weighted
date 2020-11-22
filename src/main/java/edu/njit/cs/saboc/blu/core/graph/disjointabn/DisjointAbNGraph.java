package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import javax.swing.JFrame;

/**
 * The view of some type of disjoint abstraction network.
 * 
 * @author Chris O
 * @param <T>
 */
public class DisjointAbNGraph<T extends DisjointAbstractionNetwork> extends AbstractionNetworkGraph<T> {

    public DisjointAbNGraph(
            final JFrame parentFrame, 
            final T disjointAbN, 
            final SinglyRootedNodeLabelCreator labelCreator) {
        
        super(disjointAbN, labelCreator);
        
        super.setAbstractionNetworkLayout(new DisjointAbNLayout<>(this, disjointAbN));
    }
    
    @Override
    public DisjointAbNLayout<T> getGraphLayout() {
        return (DisjointAbNLayout<T>)super.getGraphLayout();
    }

    public DisjointAbstractionNetwork getDisjointAbN() {
        return (DisjointAbstractionNetwork)getAbstractionNetwork();
    }
}
