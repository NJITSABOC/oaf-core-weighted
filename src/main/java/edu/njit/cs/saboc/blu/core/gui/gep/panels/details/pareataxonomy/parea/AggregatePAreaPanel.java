package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AggregatedNodesPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ChildAggregateNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ParentAggregateNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;


/**
 *
 * @author Chris O
 */
public class AggregatePAreaPanel extends SinglyRootedNodePanel<AggregatePArea> { 
    
    private final AggregatedNodesPanel aggregateDetailsPanel;
    
    private final int aggregateDetailsTabIndex;
    
    public AggregatePAreaPanel(PAreaTaxonomyConfiguration configuration) {

        super(new AggregatePAreaDetailsPanel(configuration),
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
    public void setContents(AggregatePArea aggregatePArea) {
        super.setContents(aggregatePArea);

        if (aggregatePArea.getAggregatedNodes().isEmpty()) {
            enableInformationTabAt(aggregateDetailsTabIndex, false);
        } else {
            this.aggregateDetailsPanel.setContents(aggregatePArea);
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
