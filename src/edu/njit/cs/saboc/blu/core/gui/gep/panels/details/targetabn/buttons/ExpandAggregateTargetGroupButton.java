package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.ExpandAggregateNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ExpandAggregateTargetGroupButton<T extends AggregateTargetGroup> extends ExpandAggregateNodeButton<T> {
    
    private final DisplayAbNAction<TargetAbstractionNetwork> displyAction;
    private final TargetAbNConfiguration config;

    public ExpandAggregateTargetGroupButton(TargetAbNConfiguration config, 
            DisplayAbNAction<TargetAbstractionNetwork> displyAction) {
        
        super(config.getTextConfiguration().getAbNTypeName(false), 
                config.getTextConfiguration().getNodeTypeName(false));
        
        this.displyAction = displyAction;
        this.config = config;
    }

    @Override
    public void expandAggregateAction() {
        if (getCurrentNode().isPresent()) {
            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread(
                    String.format("Expanding %s...", getCurrentNode().get().getName()), 
                    displyAction) {

                @Override
                public AbstractionNetwork getAbN() {
                    AggregateAbstractionNetwork disjointAbN = (AggregateAbstractionNetwork)config.getAbstractionNetwork();
                    
                    return disjointAbN.expandAggregateNode(getCurrentNode().get());
                }
            };

            display.startThread();
        }
    }

    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(node.getAggregatedNodes().size() > 0);
    }
}