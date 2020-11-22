package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.CompactNodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.CompactPAreaTaxonomyDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AggregateAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.AggregatePAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.ChildPAreaTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PAreaTaxonomyDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.ParentPAreaTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.PropertyTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyUIConfiguration extends PartitionedAbNUIConfiguration<PArea, Area> {
    
    private final PAreaTaxonomyConfiguration config;

    public PAreaTaxonomyUIConfiguration(
            PAreaTaxonomyConfiguration config, 
            AbNDisplayManager displayManager,
            PAreaTaxonomyListenerConfiguration listenerConfig,
            boolean showingAreaTaxonomy) {
        
        super(displayManager, listenerConfig, showingAreaTaxonomy);
        
        this.config = config;
    }
    
    @Override
    public PAreaTaxonomyListenerConfiguration getListenerConfiguration() {
        return (PAreaTaxonomyListenerConfiguration)super.getListenerConfiguration();
    }
    
    public PAreaTaxonomyConfiguration getConfiguration() {
        return config;
    }

    @Override
    public boolean hasPartitionedNodeDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createPartitionedNodeDetailsPanel() {
        if(config.getPAreaTaxonomy().isAggregated()) {
            return new AggregateAreaPanel(config);
        } else {
            return new AreaPanel(config);
        }
    }
    
    @Override
    public CompactNodeDashboardPanel<Area> createCompactPartitionedNodeDetailsPanel() {
        
        return new CompactNodeDashboardPanel<>(
            new NodeSummaryPanel<>(new AreaSummaryTextFactory(config)),
            this.getPartitionedNodeOptionsPanel(), 
            config);
        
    }

    @Override
    public AbNDetailsPanel createAbNDetailsPanel() {
        return new PAreaTaxonomyDetailsPanel(config);
    }

    @Override
    public CompactAbNDetailsPanel createCompactAbNDetailsPanel() {
        return new CompactPAreaTaxonomyDetailsPanel(config);
    }

    @Override
    public boolean hasNodeDetailsPanel() {
        return true;
    }

    @Override
    public NodeDashboardPanel createNodeDetailsPanel() {
        if(config.getPAreaTaxonomy().isAggregated()) {
            return new AggregatePAreaPanel(config);
        } else {
            return new PAreaPanel(config);
        }
    }

    @Override
    public CompactNodeDashboardPanel<PArea> createCompactNodeDetailsPanel() {
        
        return new CompactNodeDashboardPanel<>(
            new NodeSummaryPanel<>(new PAreaSummaryTextFactory(config)),
            this.getNodeOptionsPanel(), 
            config);
        
    }
    
    public OAFAbstractTableModel<InheritableProperty> getPropertyTableModel(boolean forArea) {
        return new PropertyTableModel(config, forArea);
    }

    @Override
    public OAFAbstractTableModel<ParentNodeDetails<PArea>> getParentNodeTableModel() {
        return new ParentPAreaTableModel(config);
    }

    @Override
    public OAFAbstractTableModel<PArea> getChildNodeTableModel() {
        return new ChildPAreaTableModel(config);
    }
}
