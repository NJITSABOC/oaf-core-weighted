package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;
import javax.swing.JPanel;

/**
 *
 * @author cro3
 */
public abstract class AbNDerivationWizardPanel extends JPanel {
    
    private Optional<Ontology<Concept>> optCurrentOntology = Optional.empty();
    
    public AbNDerivationWizardPanel() {
        
    }
    
    public void initialize(Ontology ont) {
        this.optCurrentOntology = Optional.of(ont);
    }
    
    public void clearContents() {
        this.optCurrentOntology = Optional.empty();
    }
    
    public Optional<Ontology<Concept>> getCurrentOntology() {
        return optCurrentOntology;
    }
    
    public abstract void resetView();
}
