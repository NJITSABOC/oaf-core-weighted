package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.gui.hierarchypainter.ShowHierarchyDialog;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public class ShowSourceHierarchyButton<T extends AbstractionNetwork> extends AbNOptionsButton<T> {
    
    public ShowSourceHierarchyButton() {
        super("BluViewHierarchy.png", "View source concept hierarchy.");
        
        this.addActionListener( (ae) -> {
            showSourceHierarchy();
        });
    }
    
    private void showSourceHierarchy() {
        Hierarchy<Concept> hierarchy = super.getCurrentAbN().get().getSourceHierarchy();
        
        ShowHierarchyDialog.displayHierarchyDialog(hierarchy);
    }

    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
}
