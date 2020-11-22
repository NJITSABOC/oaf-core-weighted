package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class SearchResultListModel extends OAFAbstractTableModel<SearchButtonResult> {
    public SearchResultListModel() {
        super(new String [] {
           "Search Result"
        });
    }

    @Override
    protected Object[] createRow(SearchButtonResult item) {
        return new Object [] { item };
    }
}
