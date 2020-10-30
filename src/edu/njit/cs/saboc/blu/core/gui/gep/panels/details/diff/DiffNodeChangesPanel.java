package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.BaseNodeInformationPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DiffNodeChangesPanel<T extends Node> extends BaseNodeInformationPanel<T>  {
    
    private final DiffNodeChangeList changeList;
    
    public DiffNodeChangesPanel(
            AbNConfiguration config, 
            AbNTextConfiguration textConfig) {
        
        this.setLayout(new BorderLayout());
        
        this.changeList = new DiffNodeChangeList(config, textConfig);
        this.changeList.setRightClickMenuGenerator(
                new DiffNodeChangesRightClickMenuGenerator(config, textConfig));
        
        this.add(changeList, BorderLayout.CENTER);
    }

    @Override
    public void setContents(T node) {      
        ArrayList<AbNChange> changes = new ArrayList<>();
        
        DiffNodeInstance diffNode = (DiffNodeInstance)node;
        
        changes.addAll(diffNode.getDiffNode().getChangeDetails().getConceptChanges());
        changes.addAll(diffNode.getDiffNode().getChangeDetails().getChildOfChanges());
        
        changeList.setContents(changes);
    }

    @Override
    public void clearContents() {
        changeList.clearContents();
    }
}
