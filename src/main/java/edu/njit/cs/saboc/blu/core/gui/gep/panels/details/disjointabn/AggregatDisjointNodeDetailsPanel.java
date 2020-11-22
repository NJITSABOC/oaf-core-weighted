package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class AggregatDisjointNodeDetailsPanel<T extends SinglyRootedNode> extends NodeDetailsPanel<AggregateDisjointNode<T>> {
    
    public AggregatDisjointNodeDetailsPanel(DisjointAbNConfiguration config) {
        
        super(new DisjointNodeSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new NodeConceptList(config),
                config);
    }
}
