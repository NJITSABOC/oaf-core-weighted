package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.MultiAbNGraphFrame;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistory {
    
    public interface AbNDerivationHistoryListener {
        public void historyChanged();
    }
    
    private final ArrayList<AbNDerivationHistoryEntry> entries = new ArrayList<>();
    
    private final ArrayList<AbNDerivationHistoryListener> historyChangedListeners = new ArrayList<>();
    
    private final MultiAbNGraphFrame graphFrame;
    
    public AbNDerivationHistory(MultiAbNGraphFrame graphFrame) {
        this.graphFrame = graphFrame;
    }
    
    public AbNDerivationHistory(
            MultiAbNGraphFrame graphFrame, 
            ArrayList<AbNDerivationHistoryEntry> entries) {
        
        this(graphFrame);
        
        this.entries.addAll(entries);
    }
    
    public void addHistoryChangedListener(AbNDerivationHistoryListener listener) {
        this.historyChangedListeners.add(listener);
    }
    
    public void removeHistoryChangedListener(AbNDerivationHistoryListener listener) {
        this.historyChangedListeners.remove(listener);
    }
    
    public final void setHistory(ArrayList<AbNDerivationHistoryEntry> entries) {
        
        this.entries.clear();
        this.entries.addAll(entries);

        historyChangedListeners.forEach((listener) -> {
            listener.historyChanged();
        });
    }
    
    public void addEntry(AbNDerivationHistoryEntry entry, boolean fireListeners) {


        entries.add(entry);

        if (fireListeners) {
            historyChangedListeners.forEach((listener) -> {
                listener.historyChanged();
            });
        }
        
        if(graphFrame.getWorkspace().isPresent()) {
            graphFrame.getWorkspace().get().getWorkspaceHistory().getHistory().add(entry);
            graphFrame.getWorkspace().get().saveWorkspace();
        }
    }
    
    public ArrayList<AbNDerivationHistoryEntry> getHistory() {
        return entries;
    } 
    
    public JSONArray toJSON() {
        return toJSON(entries.size());
    }
    
    public JSONArray toJSON(int entryExportLimit) {
        
        int limit = Math.min(entryExportLimit, entries.size());
        
        List<AbNDerivationHistoryEntry> sublist = entries.subList(entries.size() - limit, entries.size());
        
        JSONArray arr = new JSONArray();
        
        sublist.forEach( (entry) -> {
            arr.add(entry.toJSON());
        });
        
        return arr;
    }
}
