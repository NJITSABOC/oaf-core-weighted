package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DiffRootChangeExplanationPanel<T extends SinglyRootedNode> extends BaseNodeInformationPanel<T> {
    
    private final AbNConfiguration config;
    
    private final DiffNodeRootChangeExplanationList changeList;
    
    public DiffRootChangeExplanationPanel(
            AbNConfiguration config, 
            AbNTextConfiguration textConfig,
            ChangeExplanationRowEntryFactory factory) {
        
        this.setLayout(new BorderLayout());

        this.config = config;
        
        this.changeList = new DiffNodeRootChangeExplanationList(factory, textConfig);
        
        this.add(changeList, BorderLayout.CENTER);
    }

    @Override
    public void setContents(T node) {
        DiffAbstractionNetworkInstance diffAbN = (DiffAbstractionNetworkInstance)config.getAbstractionNetwork();
        
        DiffAbNConceptChanges structuralChanges = diffAbN.getOntologyStructuralChanges();

        Set<DiffAbNConceptChange> rootChanges = structuralChanges.getAllChangesFor(node.getRoot());
        
        ArrayList<DiffAbNConceptChange> changes = new ArrayList<>(rootChanges);
        
        this.changeList.setContents(changes);
    }

    @Override
    public void clearContents() {
        changeList.clearContents();
    }
}
