
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ContainerAggregatedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import java.util.Set;

/**
 *
 * @author cro3
 */
public class AggregateBandPanel extends PartitionedNodePanel<Band> {
    
    private final ContainerAggregatedNodePanel aggregatedNodeList;
    
    private final int aggregatedClusterPanelIndex;
    
    public AggregateBandPanel(TANConfiguration configuration) {
        
        super(new BandDetailsPanel(configuration), configuration);
        
        this.aggregatedNodeList = new ContainerAggregatedNodePanel(configuration);
        
        aggregatedClusterPanelIndex = super.addInformationTab(aggregatedNodeList, String.format("Aggregated Clusters in Band"));
    }
    
    
    @Override
    public void setContents(Band area) {
        super.setContents(area); 

        int aggregateClusterCount = 0;
        
        Set<Cluster> clusters = area.getClusters();
        
        for(Cluster cluster : clusters) {
            AggregateCluster aggregateCluster = (AggregateCluster)cluster;
            
            if(!aggregateCluster.getAggregatedNodes().isEmpty()) {
                aggregateClusterCount++;
            }
        }
        
        if(aggregateClusterCount > 0) {
            aggregatedNodeList.setContents(area);
            
            super.enableInformationTabAt(aggregatedClusterPanelIndex, true);
        }
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        aggregatedNodeList.clearContents();
        
        super.enableInformationTabAt(aggregatedClusterPanelIndex, false);
    }
}