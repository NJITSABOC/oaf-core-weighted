package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.rootselection;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class SearchForRootPanel<T extends Concept> extends RootSelectionPanel<T> {

    private final ConceptSearchPanel conceptSearchPanel;

    private Optional<OntologySearcher> optSearcher = Optional.empty();

    public SearchForRootPanel(AbNConfiguration dummyConfig) {
        super("Search for Root");

        this.setLayout(new BorderLayout());

        this.conceptSearchPanel = new ConceptSearchPanel(dummyConfig, new ConceptSearchConfiguration() {

            @Override
            public ArrayList<Concept> doSearch(ConceptSearchPanel.SearchType type, String query) {
                return searchOntology(type, query);
            }

            @Override
            public void searchResultSelected(Concept c) {
                getRootSelectionListeners().forEach( (listener) -> {
                    listener.rootSelected(c);
                });
            }

            @Override
            public void searchResultDoubleClicked(Concept c) {
                searchResultSelected(c);
            }

            @Override
            public void noSearchResultSelected() {
                getRootSelectionListeners().forEach( (listener) -> {
                    listener.noRootSelected();
                });
            }
        });

        this.add(conceptSearchPanel, BorderLayout.CENTER);
    }
    
    @Override
    public void initialize(Ontology<T> ontology, OntologySearcher searcher) {
        super.initialize(ontology, searcher);
        
        this.resetView();
        
        this.optSearcher = Optional.of(searcher);
    }
    
    @Override
    public void resetView() {
        conceptSearchPanel.resetView();
    }
    
    @Override
    public void clear() {
        super.clear();
        
        this.optSearcher = Optional.empty();
        
        this.resetView();
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.conceptSearchPanel.setEnabled(value);
    }

    public ArrayList<Concept> searchOntology(ConceptSearchPanel.SearchType type, String query) {

        ArrayList<Concept> results = new ArrayList<>();

        if (optSearcher.isPresent()) {
            query = query.trim();
            query = query.toLowerCase();

            OntologySearcher searcher = optSearcher.get();

            switch (type) {
                case Starting:
                    results.addAll(searcher.searchStarting(query));
                    break;

                case Anywhere:
                    results.addAll(searcher.searchAnywhere(query));
                    break;

                case Exact:
                    results.addAll(searcher.searchExact(query));
                    break;

                case ID:
                    results.addAll(searcher.searchID(query));
                    break;
            }
        }

        results.sort(new ConceptNameComparator());

        return results;
    }
    
    @Override
    public void selected() {
        getRootSelectionListeners().forEach((listener) -> {
            listener.noRootSelected();
        });
    }
}
