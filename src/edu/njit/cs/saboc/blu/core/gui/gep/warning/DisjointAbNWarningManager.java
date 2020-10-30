package edu.njit.cs.saboc.blu.core.gui.gep.warning;

/**
 *
 * @author Chris Ochs
 */
public class DisjointAbNWarningManager extends AbNWarningManager {
    
    private boolean showGrayWarningMessage;
    
    private boolean showOverlapsRecoloredMessage;
    
    public DisjointAbNWarningManager() {
        this.showGrayWarningMessage = true;
        this.showOverlapsRecoloredMessage = true;
    }
    
    public boolean showGrayWarningMessage() {
        return this.showGrayWarningMessage;
    }
    
    public void setShowGrayWarningMessage(boolean value) {
        this.showGrayWarningMessage = value;
    }
    
    public boolean showOverlapsRecoloredMessage() {
        return this.showOverlapsRecoloredMessage;
    }
    
    public void setShowOverlapsRecoloredMessage(boolean value) {
        this.showOverlapsRecoloredMessage = value;
    }
}
