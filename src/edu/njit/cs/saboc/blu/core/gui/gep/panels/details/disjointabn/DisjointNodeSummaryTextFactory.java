package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DisjointNodeSummaryTextFactory<T extends SinglyRootedNode> extends NodeSummaryTextFactory<DisjointNode<T>> {
    
    public DisjointNodeSummaryTextFactory(DisjointAbNConfiguration config) {
        super(config);
    }
    
    @Override
    public DisjointAbNConfiguration getConfiguration() {
        return (DisjointAbNConfiguration)super.getConfiguration();
    }
}
