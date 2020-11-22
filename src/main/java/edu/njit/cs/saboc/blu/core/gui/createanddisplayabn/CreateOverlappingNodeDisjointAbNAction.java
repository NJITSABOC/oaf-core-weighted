package edu.njit.cs.saboc.blu.core.gui.createanddisplayabn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateOverlappingNodeDisjointAbNAction extends CreateAndDisplayAbNThread {
    
    private final PartitionedAbNConfiguration config;
    private final PartitionedNode node;
    private final SinglyRootedNode singlyRootedNode;
    
    public CreateOverlappingNodeDisjointAbNAction(
            PartitionedAbNConfiguration config, 
            DisplayAbNAction displayAction, 
            PartitionedNode node, 
            SinglyRootedNode singlyRootedNode,
            String displayText) {
        
        super(displayText, displayAction);
        
        this.config = config;
        this.node = node;
        this.singlyRootedNode = singlyRootedNode;
    }

    @Override
    public AbstractionNetwork getAbN() {
        
        DisjointAbstractionNetwork<
                ? extends DisjointNode, 
                ? extends AbstractionNetwork, 
                SinglyRootedNode> disjointAbN = config.getDisjointAbstractionNetworkFor(node);
        
        return disjointAbN.getOverlappingNodeDisjointAbN(singlyRootedNode);
    }
}
