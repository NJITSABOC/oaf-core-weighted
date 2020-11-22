package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.AbNGraphFrameInitializers;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class AbNHistoryNavigationManager {
    
    private Optional<AbNGraphFrameInitializers> optInitializers = Optional.empty();
        
    private final AbNDerivationHistory history;
    
    private int currentHistoryLocation;
    
    public AbNHistoryNavigationManager(AbNDerivationHistory history) {
        this.history = history;
        this.currentHistoryLocation = -1;
        
        history.addHistoryChangedListener( () -> {
            reset();
        });
    }
    
    public final boolean canGoBack() {
        return currentHistoryLocation > 0;
    }
    
    public final boolean canGoForward() {
        return currentHistoryLocation < history.getHistory().size() - 1;
    }
    
    public final void reset() {
        if(history.getHistory().isEmpty()) {
            return;
        }
        
        currentHistoryLocation = history.getHistory().size() - 1;
    }
    
    public final void goBack() {
        
        if(!canGoBack() || !optInitializers.isPresent()) {
            return;
        }
        
        currentHistoryLocation--;
        
        history.addEntry(history.getHistory().get(currentHistoryLocation), false);

        history.getHistory().get(currentHistoryLocation).displayEntry(
            optInitializers.get().getSourceOntology());
    }
    
    public final void goForward() {
        
        if(!canGoForward() || !optInitializers.isPresent()) {
            return;
        }
        
        currentHistoryLocation++;
        
        history.getHistory().get(currentHistoryLocation).displayEntry(
                optInitializers.get().getSourceOntology());
    }
    
    public void setInitializers(AbNGraphFrameInitializers initializers) {
        this.optInitializers = Optional.of(initializers);
    }
    
    public void clearInitializers() {
        this.optInitializers = Optional.empty();
    }
}
