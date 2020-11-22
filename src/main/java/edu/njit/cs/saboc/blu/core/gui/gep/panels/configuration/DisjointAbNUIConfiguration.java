package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.CompactNodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.AggregateDisjointNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.ChildDisjointNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.DisjointNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.DisjointNodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.ParentDisjointNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class DisjointAbNUIConfiguration<T extends DisjointNode> extends AbNUIConfiguration<T> {
    
    private final DisjointAbNConfiguration<T> config;
    
    public DisjointAbNUIConfiguration(
            DisjointAbNConfiguration<T> config, 
            AbNDisplayManager abnDisplayManager,
            DisjointAbNListenerConfiguration<T> listenerConfig) {
        
        super(abnDisplayManager, listenerConfig);
        
        this.config = config;
    }
    
    public DisjointAbNConfiguration<T> getConfiguration() {
        return config;
    }

    @Override
    public OAFAbstractTableModel<ParentNodeDetails<T>> getParentNodeTableModel() {
        return new ParentDisjointNodeTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<T> getChildNodeTableModel() {
        return new ChildDisjointNodeTableModel(config);
    }

    @Override
    public AbNDetailsPanel createAbNDetailsPanel() {
        return new AbNDetailsPanel<>(config);
    }
    
    @Override
    public CompactAbNDetailsPanel createCompactAbNDetailsPanel() {
        return new CompactAbNDetailsPanel(config);
    }

    @Override
    public boolean hasNodeDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel<T> createNodeDetailsPanel() {
        
        if(config.getAbstractionNetwork().isAggregated()) {
            return new AggregateDisjointNodePanel(config);
        } else {
            return new DisjointNodePanel(config);
        }
    }
    
    @Override
    public CompactNodeDashboardPanel<T> createCompactNodeDetailsPanel() {
        
        return new CompactNodeDashboardPanel<>(
            new NodeSummaryPanel<>(new DisjointNodeSummaryTextFactory(config)),
            this.getNodeOptionsPanel(), 
            config);
        
    }
}
