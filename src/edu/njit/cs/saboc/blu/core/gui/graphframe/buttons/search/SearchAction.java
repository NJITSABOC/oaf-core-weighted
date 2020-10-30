package edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.search;

import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public abstract class SearchAction<T> {
    private final String searchActionName;
    
    public SearchAction(String searchActionName) {
        this.searchActionName = searchActionName;
    }
    
    public String getSearchActionName() {
        return searchActionName;
    }
    
    public abstract ArrayList<SearchButtonResult<T>> doSearch(String term);
    
    public abstract void resultSelected(SearchButtonResult<T> o);
}
