package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateRootSubtaxonomyButton extends CreateSubtaxonomyButton {
    
    private final PAreaTaxonomyConfiguration config;
    
    public CreateRootSubtaxonomyButton(
            PAreaTaxonomyConfiguration config, 
            DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener) {
        
        super("BluSubtaxonomy.png", "Create root subtaxonomy", displayTaxonomyListener);
        
        this.config = config;
    }

    @Override
    public PAreaTaxonomy createSubtaxonomy() {
        PArea parea = (PArea)super.getCurrentNode().get();
        
        return config.getPAreaTaxonomy().createRootSubtaxonomy(parea);
    }

    @Override
    public void setEnabledFor(PArea parea) {
        PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        this.setEnabled(!taxonomy.getPAreaHierarchy().getChildren(parea).isEmpty());
    }
}
