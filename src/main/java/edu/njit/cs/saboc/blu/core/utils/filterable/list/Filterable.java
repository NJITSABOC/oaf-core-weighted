package edu.njit.cs.saboc.blu.core.utils.filterable.list;

import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class Filterable<T> {
    
    private Optional<String> currentFilter = Optional.empty();
    
    public void setCurrentFilter(String filter) {
        currentFilter = Optional.of(filter);
    }
    
    public void clearFilter() {
        currentFilter = Optional.empty();
    }
    
    public Optional<String> getCurrentFilter() {
        return currentFilter;
    }

    public static String filter(String str, String filter) {

        filter = filter.toLowerCase();

        StringBuilder filteredText = new StringBuilder();
        filteredText.append("<html>");

        if (!filter.isEmpty()) {
            String lower = str.toLowerCase();

            int startUnfilteredText = 0;

            int startFilterText = lower.indexOf(filter, startUnfilteredText);

            while (startFilterText != -1) {
                int endFilterText = startFilterText + filter.length();

                String beforeFilter = str.substring(startUnfilteredText, startFilterText);
                String filtered = str.substring(startFilterText, endFilterText);

                filteredText.append(beforeFilter);
                filteredText.append(String.format("<font style=\"BACKGROUND-COLOR: yellow\">%s</font>", filtered));

                startUnfilteredText = endFilterText;
                startFilterText = lower.indexOf(filter, startUnfilteredText);
            }

            if (startUnfilteredText < str.length()) {
                String remaining = str.substring(startUnfilteredText);
                filteredText.append(remaining);
            }
        }

        return filteredText.toString();
    }
    
    public abstract boolean containsFilter(String filter);

    public abstract T getObject();

    public abstract String getToolTipText();

    public abstract String getClipboardText();

}
