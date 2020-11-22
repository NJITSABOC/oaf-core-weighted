package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class AggregatedNodesPanel<T extends Node> extends BaseNodeInformationPanel<T> {

    private final NodeList aggregateGroupList;
    private final ConceptList conceptList;
    
    private final JSplitPane splitPane;

    public AggregatedNodesPanel(
            NodeList groupList, 
            ConceptList conceptList, 
            AbNConfiguration configuration) {
        
        this.setLayout(new BorderLayout());
        
        this.aggregateGroupList = groupList;
        
        this.aggregateGroupList.addEntitySelectionListener(new EntitySelectionAdapter<Node>() {
            public void entityClicked(Node node) {
                
                ArrayList<Concept> sortedConcepts = new ArrayList<>(node.getConcepts());
                sortedConcepts.sort( (a, b) -> {
                    return a.getName().compareTo(b.getName());
                });
                
                conceptList.setContents(sortedConcepts);
            }
            
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });
        
        this.conceptList = conceptList;
        
        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        splitPane.setTopComponent(this.aggregateGroupList);
        splitPane.setBottomComponent(this.conceptList);
        
        this.add(splitPane, BorderLayout.CENTER);
    }
    
    @Override
    public void setContents(T node) {
        splitPane.setDividerLocation(300);
        
        AggregateNode aggregateNode = (AggregateNode)node;
        
        clearContents();
        
        if(aggregateNode.getAggregatedNodes().isEmpty()) {
            return;
        }
        
        Set<Node> aggregatedNodes = aggregateNode.getAggregatedNodes();
        
        ArrayList<Node> sortedAggregatedNodes = new ArrayList<>(aggregatedNodes);
        sortedAggregatedNodes.sort( (a, b) -> {
            
            if(a.getConceptCount() == b.getConceptCount()) {
                return a.getName().compareTo(b.getName());
            } else {
                return a.getConceptCount() - b.getConceptCount();
            }
        });
        
        aggregateGroupList.setContents(sortedAggregatedNodes); 
    }
    
    @Override
    public void clearContents() {
        aggregateGroupList.clearContents();
        conceptList.clearContents();
    }
    
}
