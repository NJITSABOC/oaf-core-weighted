package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AggregatedNodesPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.SinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ChildAggregateNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.aggregate.ParentAggregateNodeTableModel;


/**
 *
 * @author Chris O
 */
public class AggregateDisjointNodePanel<T extends SinglyRootedNode> 
        extends SinglyRootedNodePanel<AggregateDisjointNode<T>> { 
    
    private final AggregatedNodesPanel aggregateDetailsPanel;
    
    private final int aggregateDetailsTabIndex;
    
    public AggregateDisjointNodePanel(DisjointAbNConfiguration configuration) {

        super(new AggregatDisjointNodeDetailsPanel(configuration),
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
    public void setContents(AggregateDisjointNode<T> aggregatePArea) {
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
