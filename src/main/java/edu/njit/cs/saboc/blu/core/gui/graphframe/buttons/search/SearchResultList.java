package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class SearchResultList extends AbstractEntityList<SearchButtonResult> {
    public SearchResultList() {
        super(new SearchResultListModel());
    }
    
    public SearchResultList(OAFAbstractTableModel<SearchButtonResult> model) {
        super(model);
    }

    @Override
    protected String getBorderText(Optional<ArrayList<SearchButtonResult>> entities) {
        String base = "Search Results";
        
        int count = 0;
        
        if(entities.isPresent()) {
            count = entities.get().size();
        }
        
        return String.format("%s (%d)", base, count);
    }
}
