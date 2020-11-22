package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class CompactNodeDashboardPanel<T extends Node> extends BaseNodeInformationPanel<T> {
    
    private final DetailsPanelLabel nodeNameLabel;
    
    private final NodeSummaryPanel<T> nodeSummaryPanel;
    private final NodeOptionsPanel<T> nodeOptionsPanel;
    
    private final AbNConfiguration config;
    
    public CompactNodeDashboardPanel(
            NodeSummaryPanel<T> nodeSummaryPanel,
            NodeOptionsPanel<T> nodeOptionsPanel,
            AbNConfiguration config) {
        
        this.nodeNameLabel = new DetailsPanelLabel(" ");
        this.nodeSummaryPanel = nodeSummaryPanel;
        this.nodeOptionsPanel = nodeOptionsPanel;
        
        this.config = config;
        
        this.setLayout(new BorderLayout());
        
        this.add(nodeNameLabel, BorderLayout.NORTH);
        this.add(nodeSummaryPanel, BorderLayout.CENTER);
        this.add(nodeOptionsPanel, BorderLayout.SOUTH);
    }

    @Override
    public void setContents(T entity) {
        nodeNameLabel.setText(entity.getName());
        
        nodeSummaryPanel.setContents(entity);
        nodeOptionsPanel.setContents(entity);
    }

    @Override
    public void clearContents() {
        nodeNameLabel.setText(" ");
        
        nodeSummaryPanel.clearContents();
        nodeOptionsPanel.clearContents();
    }
}
