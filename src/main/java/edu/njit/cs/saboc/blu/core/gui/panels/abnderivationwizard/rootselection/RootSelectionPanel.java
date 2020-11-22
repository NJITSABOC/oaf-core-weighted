package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection;

import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection.BaseRootSelectionOptionsPanel.RootSelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class RootSelectionPanel<T extends Concept> extends JPanel {
    
    private Optional<Ontology<T>> ontology = Optional.empty();
    
    private final String selectionType;
    
    private final ArrayList<RootSelectionListener> rootSelectionListeners = new ArrayList<>();
    
    public RootSelectionPanel(String selectionType) {
        this.selectionType = selectionType;
    }
    
    public void addRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.add(rootSelectionListener);
    }
    
    public void removeRootSelectionListener(RootSelectionListener rootSelectionListener) {
        rootSelectionListeners.remove(rootSelectionListener);
    }
    
    protected ArrayList<RootSelectionListener> getRootSelectionListeners() {
        return rootSelectionListeners;
    }

    public void initialize(Ontology<T> currentOntology, OntologySearcher searcher) {
        this.ontology = Optional.of(currentOntology);
    }
    
    public void resetView() {
        
    }
    
    public Optional<Ontology<T>> getOntology() {
        return ontology;
    }
    
    public void clear() {
        this.ontology = Optional.empty();
    }
    
    public String getSelectionType() {
        return selectionType;
    }
    
    public abstract void selected();
}
