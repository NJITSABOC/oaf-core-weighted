package edu.njit.cs.saboc.blu.core.gui.gep.warning;

/**
 *
 * @author Chris Ochs
 */
public class AbNWarningManager {
    
    private boolean showAggregationWarning;
    
    public AbNWarningManager() {
        this.showAggregationWarning = true;
    }
    
    public void setShowAggregationWarning(boolean value) {
        this.showAggregationWarning = value;
    }
    
    public boolean showAggregationWarning() {
        return this.showAggregationWarning;
    }
}
