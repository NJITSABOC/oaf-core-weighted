package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.ConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SelectOntologyRootPanel<T extends Concept> extends RootSelectionPanel<T> {
    
    private final ConceptList ontologyRootList;
    
    public SelectOntologyRootPanel(
            String title,
            AbNConfiguration dummyConfig) {
        
        super(title);

        this.ontologyRootList = new ConceptList(dummyConfig);
        this.ontologyRootList.addEntitySelectionListener(new EntitySelectionListener<Concept>() {

            @Override
            public void entityClicked(Concept c) {
                getRootSelectionListeners().forEach((listener) -> {
                    listener.rootSelected(c);
                });
            }

            @Override
            public void entityDoubleClicked(Concept c) {
                getRootSelectionListeners().forEach((listener) -> {
                    listener.rootDoubleClicked(c);
                });
            }

            @Override
            public void noEntitySelected() {
                getRootSelectionListeners().forEach((listener) -> {
                    listener.noRootSelected();
                });
            }
        });
                
        this.setLayout(new BorderLayout());
        
        this.add(this.ontologyRootList, BorderLayout.CENTER);
    }
    
    public SelectOntologyRootPanel(
            AbNConfiguration dummyConfig) {
        
        this("Select Ontology Root", dummyConfig);
    }
    
    protected void setRootList(ArrayList<Concept> roots) {
        this.ontologyRootList.setContents(roots);
    }
            
    @Override
    public void initialize(Ontology<T> ontology, OntologySearcher searcher) {
        
        super.initialize(ontology, searcher);
        
        ArrayList<Concept> roots = new ArrayList<>(
                ontology.getConceptHierarchy().getChildren(
                    ontology.getConceptHierarchy().getRoot()));
        
        roots.sort(new ConceptNameComparator());
        
        this.ontologyRootList.setContents(roots);
    }
    
    @Override
    public void resetView() {
        this.ontologyRootList.clearContents();
    }
    
    @Override
    public void clear() {
        super.clear();
        
        this.resetView();
    }

    @Override
    public void selected() {
        
        if(this.ontologyRootList.getSelectedItem().isPresent()) {
            getRootSelectionListeners().forEach((listener) -> {
                listener.rootSelected(this.ontologyRootList.getSelectedItem().get());
            });
        } else {
            getRootSelectionListeners().forEach((listener) -> {
                listener.noRootSelected();
            });
        }
    }
}
