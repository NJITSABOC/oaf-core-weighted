package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;


/**
 *
 * @author Chris O
 */
public class SinglyRootedNodePanel<T extends SinglyRootedNode> extends NodeDashboardPanel<T> {

    private final NodeHierarchyPanel<T> groupHierarchyPanel;
    
    private final ConceptHierarchyPanel<T> conceptHierarchyPanel;

    public SinglyRootedNodePanel(
            NodeDetailsPanel<T> groupDetailsPanel, 
            NodeHierarchyPanel<T> groupHierarchyPanel, 
            ConceptHierarchyPanel<T> conceptHierarchyPanel,
            AbNConfiguration configuration) {
        
        super(groupDetailsPanel, configuration);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addInformationTab(groupHierarchyPanel, String.format("%s Hierarchy", 
                configuration.getTextConfiguration().getNodeTypeName(false)));
        
        this.conceptHierarchyPanel = conceptHierarchyPanel;
        
        addInformationTab(conceptHierarchyPanel, String.format("%s's %s Hierarchy", 
                configuration.getTextConfiguration().getNodeTypeName(false),
                configuration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)));
    }
}
