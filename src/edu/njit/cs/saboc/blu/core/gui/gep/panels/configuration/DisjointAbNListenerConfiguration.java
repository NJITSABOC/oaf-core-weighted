package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.NavigateToNodeListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.ParentNodeSelectedListener;

/**
 *
 * @author Chris O
 */
public abstract class DisjointAbNListenerConfiguration<T extends DisjointNode> extends AbNListenerConfiguration<T> {
    
    public DisjointAbNListenerConfiguration(DisjointAbNConfiguration config) {
        super(config);
    }
    
    @Override
    public EntitySelectionListener<T> getChildGroupListener() {
        return new NavigateToNodeListener<>(super.getConfiguration());
    }

    @Override
    public EntitySelectionListener<ParentNodeDetails<T>> getParentGroupListener() {
        return new ParentNodeSelectedListener(super.getConfiguration());
    }
}
