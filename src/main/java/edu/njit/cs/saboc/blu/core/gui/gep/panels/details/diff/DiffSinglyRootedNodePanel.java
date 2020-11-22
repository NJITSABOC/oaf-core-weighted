package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;

/**
 *
 * @author Chris O
 */
public class DiffSinglyRootedNodePanel<T extends SinglyRootedNode> extends NodeDashboardPanel<T> {

    private final NodeHierarchyPanel<T> groupHierarchyPanel;
    
    private final DiffNodeChangesPanel<T> diffNodeChangesPanel;
    
    private final DiffRootChangeExplanationPanel<T> rootChangesPanel;
    private final int changeExplanationPanelIndex;
    
    public DiffSinglyRootedNodePanel(
            NodeDetailsPanel<T> groupDetailsPanel, 
            NodeHierarchyPanel<T> groupHierarchyPanel, 
            ChangeExplanationRowEntryFactory changeExplanationFactory,
            AbNConfiguration configuration) {
        
        super(groupDetailsPanel, configuration);
        
        this.groupHierarchyPanel = groupHierarchyPanel;

        addInformationTab(groupHierarchyPanel, String.format("%s Hierarchy", 
                configuration.getTextConfiguration().getNodeTypeName(false)));
        
        this.diffNodeChangesPanel = new DiffNodeChangesPanel<>(
                configuration,
                configuration.getTextConfiguration());
        
        addInformationTab(diffNodeChangesPanel, String.format("%s Changes", 
                configuration.getTextConfiguration().getNodeTypeName(false)));
        
        this.rootChangesPanel = new DiffRootChangeExplanationPanel<>(
                configuration, 
                configuration.getTextConfiguration(),
                changeExplanationFactory);
        
        changeExplanationPanelIndex = addInformationTab(rootChangesPanel, String.format("%s Change Explanation",
                configuration.getTextConfiguration().getNodeTypeName(false)));
    }

    @Override
    public void setContents(T node) {
        super.setContents(node);
        
        DiffAbstractionNetworkInstance diffAbN = (DiffAbstractionNetworkInstance)super.getConfiguration().getAbstractionNetwork();

        this.enableInformationTabAt(changeExplanationPanelIndex, !diffAbN.getOntologyStructuralChanges().getAllChangesFor(node.getRoot()).isEmpty());
    }
}