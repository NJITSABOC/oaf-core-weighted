package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.targetbased;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.OntologySearcher;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.SubhierarchySearcher;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import edu.njit.cs.saboc.blu.core.utils.comparators.ConceptNameComparator;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author Chris O
 */
public class TargetSubhierarchyRootSelectionPanel extends AbNDerivationWizardPanel {

    public static enum TargetRootSelectionMode {
        WholeSubhierarchy,
        SearchForRoot
    }
            
    private final ConceptSearchPanel conceptSearchPanel;
    
    private final JToggleButton btnUseWholeSubhierarchy;
    private final JToggleButton btnSearchForRoot;
    
    
    private Optional<Concept> selectedRoot = Optional.empty();
    
    private Optional<SubhierarchySearcher> optSearcher = Optional.empty();
    
    private TargetRootSelectionMode targetRootSelectionMode = TargetRootSelectionMode.WholeSubhierarchy;

    public TargetSubhierarchyRootSelectionPanel(AbNConfiguration config) {
        this.setLayout(new BorderLayout());
                
        this.conceptSearchPanel = new ConceptSearchPanel(config, new ConceptSearchConfiguration() {

            @Override
            public ArrayList<Concept> doSearch(ConceptSearchPanel.SearchType type, String query) {
                return searchOntology(type, query);
            }

            @Override
            public void searchResultSelected(Concept c) {
                selectedRoot = Optional.of(c);
            }

            @Override
            public void searchResultDoubleClicked(Concept c) {
                searchResultSelected(c);
            }

            @Override
            public void noSearchResultSelected() {
                selectedRoot = Optional.empty();
            }
        });

        this.btnUseWholeSubhierarchy = new JToggleButton("Use Complete Target Subhierarchy");
        this.btnUseWholeSubhierarchy.addActionListener( (ae) -> {
            useEntireSubhierarchy();
        });
        
        this.btnSearchForRoot = new JToggleButton("Search for a Root");
        this.btnSearchForRoot.addActionListener( (ae) -> {
            useSpecificRootSelected();
        });
        
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(btnUseWholeSubhierarchy);
        buttonGroup.add(btnSearchForRoot);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(btnUseWholeSubhierarchy);
        northPanel.add(btnSearchForRoot);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(conceptSearchPanel, BorderLayout.CENTER);
        
        resetView();
    }
    
    
    public Optional<Concept> getSelectedRoot() {
        return selectedRoot;
    }
    
    public TargetRootSelectionMode getRootSelectionMode() {
        return targetRootSelectionMode;
    }

    public void initialize(Ontology ontology, SubhierarchySearcher searcher) {        
        super.initialize(ontology);
        
        this.optSearcher = Optional.of(searcher);
        
        btnUseWholeSubhierarchy.setSelected(true);
        
        resetView();
    }
    
    @Override
    public void clearContents() {
        super.clearContents();
        
        this.selectedRoot = Optional.empty();
        this.optSearcher = Optional.empty();
    }

    @Override
    public final void resetView() {
        conceptSearchPanel.resetView();
        
        btnUseWholeSubhierarchy.setSelected(true);
        
        if(btnSearchForRoot.isSelected()) {
            useSpecificRootSelected();
        } else {
            useEntireSubhierarchy();
        }
    }

    private void useEntireSubhierarchy() {
        
        this.targetRootSelectionMode = TargetRootSelectionMode.WholeSubhierarchy;
        
        this.btnSearchForRoot.setSelected(false);
        
        this.conceptSearchPanel.setEnabled(false);
        this.conceptSearchPanel.clearResults();
        
        if (super.getCurrentOntology().isPresent()) {
            Concept root = super.getCurrentOntology().get().getConceptHierarchy().getRoot();
            
            this.selectedRoot = Optional.of(root);
        } else {
            this.selectedRoot = Optional.empty();
        }
    }
    
    private void useSpecificRootSelected() {
        this.targetRootSelectionMode = TargetRootSelectionMode.SearchForRoot;
        
        this.conceptSearchPanel.setEnabled(true);
    }
    
    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
        
        this.btnUseWholeSubhierarchy.setEnabled(value);
        this.btnSearchForRoot.setEnabled(value);
        
        this.conceptSearchPanel.setEnabled(value && btnSearchForRoot.isSelected());
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
}
