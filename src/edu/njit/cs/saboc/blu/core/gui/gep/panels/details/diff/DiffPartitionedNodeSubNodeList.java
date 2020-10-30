package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeConceptListModel;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JSplitPane;

/**
 *
 * @author Chris O
 */
public class DiffPartitionedNodeSubNodeList<T extends PartitionedNode, V extends SinglyRootedNode> extends BaseNodeInformationPanel<T> {
    
    private final JSplitPane splitPane;

    private final DiffNodeList<V> nodeList;
    
    private final NodeConceptList<V> conceptList;
    
    private final PartitionedAbNConfiguration configuration;
    
    public DiffPartitionedNodeSubNodeList(PartitionedAbNConfiguration configuration) {
        
        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());

        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        this.nodeList = new DiffNodeList<>(configuration);
        
        this.nodeList.addEntitySelectionListener(new EntitySelectionAdapter<V>() {
            public void entityClicked(V node) {
                conceptList.setCurrentNode(node);
                
                ArrayList<Concept> sortedConcepts = new ArrayList<>(node.getConcepts());
                sortedConcepts.sort((a,b) -> a.getName().compareTo(b.getName()));
                
                conceptList.setContents(sortedConcepts);
            }
            
            public void noEntitySelected() {
                conceptList.clearContents();
            }
        });
        
        DiffNodeConceptListModel listModel = new DiffNodeConceptListModel(
                configuration,
                configuration.getTextConfiguration());
        
        this.conceptList = new NodeConceptList<>(listModel, configuration);
        
        this.conceptList.setRightClickMenuGenerator(
                new DiffNodeConceptRightClickMenuGenerator(
                    configuration, 
                    configuration.getTextConfiguration(),
                    listModel));
        
        splitPane.setTopComponent(nodeList);
        splitPane.setBottomComponent(conceptList);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    protected DiffNodeList<V> getGroupList() {
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