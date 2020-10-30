package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public class PartitionedNodeSubNodeList<T extends PartitionedNode, V extends SinglyRootedNode> extends BaseNodeInformationPanel<T> {
    
    private final JSplitPane splitPane;

    private final NodeList<V> nodeList;
    
    private final ConceptList conceptList;
    
    private final PartitionedAbNConfiguration configuration;
    
    public PartitionedNodeSubNodeList(PartitionedAbNConfiguration configuration) {
        
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());

        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        this.nodeList = new NodeList(configuration);
        
        this.nodeList.addEntitySelectionListener(new EntitySelectionAdapter<V>() {
            
            @Override
            public void entityClicked(V node) {
                ArrayList<Concept> sortedConcepts = new ArrayList<>(node.getConcepts());
                sortedConcepts.sort((a,b) -> a.getName().compareTo(b.getName()));
                
                conceptList.setContents(sortedConcepts);
            }
            
            @Override
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });
        
        this.conceptList = new ConceptList(configuration);
        
        splitPane.setTopComponent(nodeList);
        splitPane.setBottomComponent(conceptList);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    protected NodeList getGroupList() {
        return nodeList;
    }

    @Override
    public void setContents(T partitionedNode) {
        
        splitPane.setDividerLocation(300);
        
        ArrayList<V> sortedNodes = new ArrayList<>(partitionedNode.getInternalNodes());
        
        sortedNodes.sort((a,b) -> a.getName().compareTo(b.getName()));
        
        nodeList.setContents(sortedNodes);
        
        conceptList.clearContents();
    }

    @Override
    public void clearContents() {
        nodeList.clearContents();
        conceptList.clearContents();
    }
}
