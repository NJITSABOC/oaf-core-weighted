
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.ExpandAggregateNodeButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class ExpandAggregateDisjointNodeButton<T extends AggregateDisjointNode> extends ExpandAggregateNodeButton<T> {
    
    private final DisplayAbNAction<AggregateDisjointAbstractionNetwork> disjointDisplayAction;
    private final DisjointAbNConfiguration config;

    public ExpandAggregateDisjointNodeButton(DisjointAbNConfiguration config, 
            DisplayAbNAction<AggregateDisjointAbstractionNetwork> disjointDisplayAction) {
        
        super(config.getTextConfiguration().getAbNTypeName(false), 
                config.getTextConfiguration().getNodeTypeName(false));
        
        this.disjointDisplayAction = disjointDisplayAction;
        this.config = config;
    }

    @Override
    public void expandAggregateAction() {
        if (getCurrentNode().isPresent()) {
            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread(
                    String.format("Expanding %s...", getCurrentNode().get().getName()), 
                    disjointDisplayAction) {

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
