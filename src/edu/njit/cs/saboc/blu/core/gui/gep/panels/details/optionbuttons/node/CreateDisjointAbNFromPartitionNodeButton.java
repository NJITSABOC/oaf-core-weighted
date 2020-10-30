package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.DisplayDisjointAbNAction;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CreateDisjointAbNFromPartitionNodeButton<T extends Node> extends NodeOptionButton<T> {

    private final PartitionedAbNConfiguration config;
    private final DisplayAbNAction<DisjointAbstractionNetwork> displayAbNAction;

    public CreateDisjointAbNFromPartitionNodeButton(
            PartitionedAbNConfiguration config,
            DisplayAbNAction<DisjointAbstractionNetwork> displayAbNAction) {

        super("BluDisjointAbN.png",
                String.format("Display disjoint %s",
                        config.getTextConfiguration().getAbNTypeName(false)));

        this.config = config;
        this.displayAbNAction = displayAbNAction;

        this.addActionListener((ae) -> {
            createAndDisplayDisjointAbNAction();
        });
    }

    public final void createAndDisplayDisjointAbNAction() {
        final PartitionedNode node = (PartitionedNode) super.getCurrentNode().get();
       
        DisplayDisjointAbNAction display = new DisplayDisjointAbNAction(
                config, 
                displayAbNAction, 
                node, 
                String.format("Creating %s Disjoint %s",
                    node.getName(),
                    config.getTextConfiguration().getAbNTypeName(false)));
        
        display.startThread();
    }

    @Override
    public void setEnabledFor(Node node) {
        PartitionedNode partitionedNode = (PartitionedNode) node;

        this.setEnabled(partitionedNode.hasOverlappingConcepts());
    }
}
