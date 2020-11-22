
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ContainerAggregatedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import java.util.Set;

/**
 *
 * @author Den
 */
public class AggregateAreaPanel extends PartitionedNodePanel<Area> {
    
    private final ContainerAggregatedNodePanel aggregatedNodeList;
    
    private final int aggregatedPAreaPanelIndex;
    
    public AggregateAreaPanel(PAreaTaxonomyConfiguration configuration) {
        
        super(new AggregateAreaDetailsPanel(configuration), configuration);
        
        this.aggregatedNodeList = new ContainerAggregatedNodePanel(configuration);
        
        aggregatedPAreaPanelIndex = super.addInformationTab(aggregatedNodeList, String.format("Aggregated Partial-areas in Area"));
    }
    
    
    @Override
    public void setContents(Area area) {
        super.setContents(area); 

        int aggregatePAreaCount = 0;
        
        Set<PArea> pareas = area.getPAreas();
        
        for(PArea parea : pareas) {
            AggregatePArea aggregatePArea = (AggregatePArea)parea;
            
            if(!aggregatePArea.getAggregatedNodes().isEmpty()) {
                aggregatePAreaCount++;
            }
        }
        
        if(aggregatePAreaCount > 0) {
            aggregatedNodeList.setContents(area);
            
            super.enableInformationTabAt(aggregatedPAreaPanelIndex, true);
        }
    }

    @Override
    public void clearContents() {
        super.clearContents(); 
        
        aggregatedNodeList.clearContents();
        
        super.enableInformationTabAt(aggregatedPAreaPanelIndex, false);
    }
    
}
