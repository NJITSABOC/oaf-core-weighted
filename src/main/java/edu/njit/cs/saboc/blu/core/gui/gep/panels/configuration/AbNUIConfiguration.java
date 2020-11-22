package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy.ConceptPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.CompactNodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class AbNUIConfiguration<T extends Node> {
    
    private final AbNListenerConfiguration<T> listenerConfiguration;
    
    private final AbNDisplayManager abnDisplayManager;
    
    private AbNDisplayPanel abnDisplayPanel;
    
    protected AbNUIConfiguration(
            AbNDisplayManager abnDisplayManager,
            AbNListenerConfiguration<T> listenerConfiguration) {
        
        this.listenerConfiguration = listenerConfiguration;
        this.abnDisplayManager = abnDisplayManager;
    }
    
    public void setDisplayPanel(AbNDisplayPanel abnDisplayPanel) {
        this.abnDisplayPanel = abnDisplayPanel;
    }
    
    public AbNDisplayPanel getDisplayPanel() {
        return abnDisplayPanel;
    }
    
    public AbNDisplayManager getAbNDisplayManager() {
        return abnDisplayManager;
    }
    
    public AbNListenerConfiguration<T> getListenerConfiguration() {
        return listenerConfiguration;
    }
    
    public AbNOptionsPanel getAbNOptionsPanel() {
        return new AbNOptionsPanel();
    }
    
    public abstract OAFAbstractTableModel<ParentNodeDetails<T>> getParentNodeTableModel();
    public abstract OAFAbstractTableModel<T> getChildNodeTableModel();
    
    public abstract NodeOptionsPanel<T> getNodeOptionsPanel();
    
    public abstract ConceptPainter getConceptHierarchyPainter();
    
    public abstract AbNDetailsPanel createAbNDetailsPanel();
    
    public abstract CompactAbNDetailsPanel createCompactAbNDetailsPanel();
    
    public abstract boolean hasNodeDetailsPanel();
    public abstract NodeDashboardPanel<T> createNodeDetailsPanel();
    public abstract CompactNodeDashboardPanel<T> createCompactNodeDetailsPanel();
}
