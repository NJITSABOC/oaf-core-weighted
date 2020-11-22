package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.NavigateToContainerReportListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.NavigateToNodeListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.ParentNodeSelectedListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNListenerConfiguration<T extends SinglyRootedNode, 
        V extends PartitionedNode> extends AbNListenerConfiguration<T> {
    
    public PartitionedAbNListenerConfiguration(PartitionedAbNConfiguration config) {
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
    
    public EntitySelectionListener<ContainerReport> getContainerReportSelectedListener() {
        return new NavigateToContainerReportListener(super.getConfiguration());
    }
}
