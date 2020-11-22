
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.label.DetailsPanelLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class NodeDashboardPanel<T extends Node> extends BaseNodeInformationPanel<T> {
    
    private final DetailsPanelLabel nodeNameLabel;
    
    private final ArrayList<BaseNodeInformationPanel<T>> nodeInformationPanels = new ArrayList<>();
    
    private final JTabbedPane tabbedPane;
    
    private final NodeDetailsPanel<T> nodeDetailsPanel;
    
    private final AbNConfiguration<T> configuration;

    public NodeDashboardPanel(
            NodeDetailsPanel<T> nodeDetailsPanel, 
            AbNConfiguration<T> configuration) {
        
        this.configuration = configuration;
        this.nodeDetailsPanel = nodeDetailsPanel;
        
        this.setLayout(new BorderLayout());
        this.setOpaque(false);
        
        nodeNameLabel = new DetailsPanelLabel(" ");
        
        tabbedPane = new JTabbedPane();
        
        addInformationTab(this.nodeDetailsPanel, String.format("%s Details", configuration.getTextConfiguration().getNodeTypeName(false)));

        this.add(nodeNameLabel, BorderLayout.NORTH);
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public AbNConfiguration<T> getConfiguration() {
        return configuration;
    }
    
    @Override
    public void setContents(T node) {
        nodeNameLabel.setText(node.getName());
        
        nodeInformationPanels.forEach((gdp) -> {
            gdp.setContents(node);
        });
    }
    
    @Override
    public void clearContents() {
        nodeNameLabel.setText("");
        
        nodeInformationPanels.forEach((BaseNodeInformationPanel gdp) -> {
            gdp.clearContents();
        });
    }
    
    public final int addInformationTab(BaseNodeInformationPanel<T> panel, String tabName) {
        tabbedPane.addTab(tabName, panel);
        nodeInformationPanels.add(panel);
        
        return tabbedPane.getTabCount() - 1;
    }
    
    public final void enableInformationTabAt(int index, boolean enabled) {
        tabbedPane.setEnabledAt(index, enabled);
        
        if(!enabled && tabbedPane.getSelectedIndex() == index) {
            tabbedPane.setSelectedIndex(0);
        }
    }
}
