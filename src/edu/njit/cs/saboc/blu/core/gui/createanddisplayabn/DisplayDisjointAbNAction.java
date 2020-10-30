package edu.njit.cs.saboc.blu.core.gui.createanddisplayabn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

public class DisplayDisjointAbNAction extends CreateAndDisplayAbNThread {

    private final PartitionedAbNConfiguration config;
    private final PartitionedNode node;

    public DisplayDisjointAbNAction(
            PartitionedAbNConfiguration config, 
            DisplayAbNAction displayAction, 
            PartitionedNode node, 
            String displayText) {
        
        super(displayText, displayAction);
        
        this.config = config;
        this.node = node;
    }

    @Override
    public AbstractionNetwork getAbN() {
        return config.getDisjointAbstractionNetworkFor(node);
    }
}
