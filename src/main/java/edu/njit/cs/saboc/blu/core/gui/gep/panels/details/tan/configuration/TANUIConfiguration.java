package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.CompactNodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.ChildClusterTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.ParentClusterTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.TANDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band.AggregateBandPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band.BandPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band.BandSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster.AggregateClusterPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster.ClusterPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster.ClusterSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 */
public abstract class TANUIConfiguration extends PartitionedAbNUIConfiguration<Cluster, Band> {
    
    private final TANConfiguration config;
    
    public TANUIConfiguration(
            TANConfiguration config, 
            AbNDisplayManager displayManager,
            TANListenerConfiguration listenerConfig,
            boolean showingBandTAN) {
        
        super(displayManager, listenerConfig, showingBandTAN);
        
        this.config = config;
    }
    
    public TANConfiguration getConfiguration() {
        return config;
    }
    
    @Override
    public TANListenerConfiguration getListenerConfiguration() {
        return (TANListenerConfiguration)super.getListenerConfiguration();
    }
    
    @Override
    public OAFAbstractTableModel<ParentNodeDetails<Cluster>> getParentNodeTableModel() {
        return new ParentClusterTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<Cluster> getChildNodeTableModel() {
        return new ChildClusterTableModel(config);
    }

    @Override
    public boolean hasPartitionedNodeDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createPartitionedNodeDetailsPanel() {
        if(config.getTribalAbstractionNetwork().isAggregated()) {
            return new AggregateBandPanel(config);
        } else {
            return new BandPanel(config);
        }
    }
    
    @Override
    public CompactNodeDashboardPanel<Band> createCompactPartitionedNodeDetailsPanel() {
        
        return new CompactNodeDashboardPanel<>(
            new NodeSummaryPanel<>(new BandSummaryTextFactory(config)),
            this.getPartitionedNodeOptionsPanel(), 
            config);
        
    }

    @Override
    public AbNDetailsPanel createAbNDetailsPanel() {
        return new TANDetailsPanel(config);
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
    public NodeDashboardPanel createNodeDetailsPanel() {
        if(config.getTribalAbstractionNetwork().isAggregated()) {
            return new AggregateClusterPanel(config);
        } else {
            return new ClusterPanel(config);
        }
    }
    
    @Override
    public CompactNodeDashboardPanel<Cluster> createCompactNodeDetailsPanel() {
        
        return new CompactNodeDashboardPanel<>(
            new NodeSummaryPanel<>(new ClusterSummaryTextFactory(config)),
            this.getNodeOptionsPanel(), 
            config);
        
    }
}