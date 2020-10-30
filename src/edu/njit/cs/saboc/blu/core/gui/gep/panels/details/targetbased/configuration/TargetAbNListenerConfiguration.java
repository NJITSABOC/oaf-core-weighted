package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNListenerConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.NavigateToNodeListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.ParentNodeSelectedListener;

/**
 *
 * @author Chris O
 */
public class TargetAbNListenerConfiguration extends AbNListenerConfiguration<TargetGroup> {
    
    public TargetAbNListenerConfiguration(TargetAbNConfiguration config) {
        super(config);
    }

    @Override
    public EntitySelectionListener<TargetGroup> getChildGroupListener() {
        return new NavigateToNodeListener<>(super.getConfiguration());
    }

    @Override
    public EntitySelectionListener<ParentNodeDetails<TargetGroup>> getParentGroupListener() {
        return new ParentNodeSelectedListener(super.getConfiguration());
    }
}
