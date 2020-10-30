
package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CompleteOntologyRootPanel<T extends Concept> extends RootSelectionPanel<T> {
    
    public CompleteOntologyRootPanel() {
        super("Use Complete Ontology");
    }
    
    @Override
    public void selected() {
        
        if (getOntology().isPresent()) {
            getRootSelectionListeners().forEach((listener) -> {
                listener.rootSelected(getOntology().get().getConceptHierarchy().getRoot());
            });
        }
        
    }
}
