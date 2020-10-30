package edu.njit.cs.saboc.blu.core.gui.panels;

import edu.njit.cs.saboc.blu.core.gui.panels.ConceptSearchPanel.SearchType;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public interface ConceptSearchConfiguration {
    public ArrayList<Concept> doSearch(SearchType type, String query);
    
    public void searchResultSelected(Concept c);
    
    public void searchResultDoubleClicked(Concept c);
    
    public void noSearchResultSelected();
}
