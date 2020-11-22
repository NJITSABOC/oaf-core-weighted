
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.ExpandAggregateNodeButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class ExpandAggregateClusterButton<T extends AggregateCluster> extends ExpandAggregateNodeButton<T> {
    
    private final DisplayAbNAction<ClusterTribalAbstractionNetwork> disjointDisplayAction;
    private final TANConfiguration config;

    public ExpandAggregateClusterButton(TANConfiguration config, 
            DisplayAbNAction<ClusterTribalAbstractionNetwork> disjointDisplayAction) {
        
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
                            AggregateAbstractionNetwork disjointAbN = (AggregateAbstractionNetwork) config.getAbstractionNetwork();

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
