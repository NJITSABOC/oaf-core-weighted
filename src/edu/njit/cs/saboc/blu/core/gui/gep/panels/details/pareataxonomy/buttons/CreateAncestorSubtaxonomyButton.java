package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateAncestorSubtaxonomyButton extends CreateSubtaxonomyButton {
    
    private final PAreaTaxonomyConfiguration config;

    public CreateAncestorSubtaxonomyButton(PAreaTaxonomyConfiguration config, DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener) {
        super("BluAncestorSubtaxonomy.png", 
                "Create ancestor subtaxonomy", 
                displayTaxonomyListener);
        
        this.config = config;
    }
    
    @Override
    public PAreaTaxonomy createSubtaxonomy() {
        PArea parea = super.getCurrentNode().get();
        
        return config.getPAreaTaxonomy().createAncestorSubtaxonomy(parea);
    }

    @Override
    public void setEnabledFor(PArea parea) {

        PAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        if(taxonomy.getPAreaHierarchy().getParents(parea).isEmpty()) {
            this.setEnabled(false);
        } else {
            this.setEnabled(true);
        }
    }
}
