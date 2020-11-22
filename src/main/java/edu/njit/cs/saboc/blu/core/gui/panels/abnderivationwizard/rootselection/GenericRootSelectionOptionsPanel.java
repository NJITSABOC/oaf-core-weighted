package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 *
 * @author Chris O
 */
public class GenericRootSelectionOptionsPanel extends BaseRootSelectionOptionsPanel {
    
    private final CompleteOntologyRootPanel completeOntology;
    private final SelectOntologyRootPanel selectRoot;
    private final SearchForRootPanel searchForRoot;
    
    public GenericRootSelectionOptionsPanel(AbNConfiguration config) {
        this.completeOntology = new CompleteOntologyRootPanel();
        this.selectRoot = new SelectOntologyRootPanel(config);
        this.searchForRoot = new SearchForRootPanel(config);
        
        super.addRootSelectionOption(completeOntology);
        super.addRootSelectionOption(selectRoot);
        super.addRootSelectionOption(searchForRoot);
    }
}
