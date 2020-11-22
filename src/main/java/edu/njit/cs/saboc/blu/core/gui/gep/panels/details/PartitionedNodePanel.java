package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.OverlappingConceptDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class PartitionedNodePanel<T extends PartitionedNode> extends NodeDashboardPanel<T> {

    private final PartitionedNodeSubNodeList groupListPanel;
    
    private final DisjointAbNMetricsPanel<T> disjointMetricsPanel;
    
    private final int disjointMetricsTabIndex;

    public PartitionedNodePanel(
            NodeDetailsPanel<T> containerDetailsPanel, 
            PartitionedAbNConfiguration configuration) {
        
        super(containerDetailsPanel, configuration);
        
        this.groupListPanel = new PartitionedNodeSubNodeList(configuration);
        
        String subnodeListTabTitle = String.format("%s's %s", 
                configuration.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(false), 
                configuration.getTextConfiguration().getNodeTypeName(true));
        
        super.addInformationTab(groupListPanel, subnodeListTabTitle);
        
        this.disjointMetricsPanel = new DisjointAbNMetricsPanel(configuration, DisjointAbNMetricsPanel.PanelOrientation.Vertical);
        
        String overlappingTabTitle = String.format("Overlapping %s Metrics", 
                configuration.getTextConfiguration().getNodeTypeName(false));
                
        this.disjointMetricsTabIndex = super.addInformationTab(disjointMetricsPanel, overlappingTabTitle);
    }

    @Override
    public void clearContents() {
        super.clearContents();
        
        groupListPanel.clearContents();
        disjointMetricsPanel.clearContents();
        
        this.enableInformationTabAt(disjointMetricsTabIndex, true);
    }

    @Override
    public void setContents(T node) {
        super.setContents(node);
        
        PartitionedNode partitionedNode = (PartitionedNode)node;

        Set<OverlappingConceptDetails> overlaps = partitionedNode.getOverlappingConceptDetails();
        
        this.enableInformationTabAt(disjointMetricsTabIndex, !overlaps.isEmpty());
    }
}
