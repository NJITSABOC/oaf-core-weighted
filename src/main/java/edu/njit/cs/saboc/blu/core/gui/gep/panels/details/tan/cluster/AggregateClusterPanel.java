
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AggregatedNodesPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ChildAggregateNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ParentAggregateNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateClusterPanel extends SinglyRootedNodePanel<AggregateCluster> { 
    
    private final AggregatedNodesPanel aggregateDetailsPanel;
    
    private final int aggregateDetailsTabIndex;
    
    public AggregateClusterPanel(TANConfiguration configuration) {

        super(new AggregateClusterDetailsPanel(configuration),
                new NodeHierarchyPanel(configuration, 
                        new ParentAggregateNodeTableModel(configuration), 
                        new ChildAggregateNodeTableModel(configuration)),
               
                new ConceptHierarchyPanel(configuration), 
                configuration);
        
        this.aggregateDetailsPanel = new AggregatedNodesPanel(
                new NodeList(configuration),
                new ConceptList(configuration), 
                configuration);
        
        String tabTitle =  String.format(
            "Aggregated %s", configuration.getTextConfiguration().getNodeTypeName(true));
        
        this.aggregateDetailsTabIndex = super.addInformationTab(aggregateDetailsPanel, tabTitle);
    }
    
    @Override
    public void setContents(AggregateCluster aggregateCluster) {
        super.setContents(aggregateCluster);

        if (aggregateCluster.getAggregatedNodes().isEmpty()) {
            enableInformationTabAt(aggregateDetailsTabIndex, false);
        } else {
            this.aggregateDetailsPanel.setContents(aggregateCluster);
            this.enableInformationTabAt(aggregateDetailsTabIndex, true);
        }
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        aggregateDetailsPanel.clearContents();
        this.enableInformationTabAt(aggregateDetailsTabIndex, false);
    }
}