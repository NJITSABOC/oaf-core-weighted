package edu.njit.cs.saboc.blu.core.graph.nodes;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public abstract class AbNNodeEntry extends JPanel {

    public static enum HighlightState {
        None,
        Selected,
        Parent,
        Child,
        SearchResult
    }
    
    private boolean isMousedOver;
    
    private HighlightState highlightState;
    
    private final Node node; 
    
    public AbNNodeEntry(Node node) {
        this.node = node;
        
        this.isMousedOver = false;
        this.highlightState = HighlightState.None;
    }
    
    public Node getNode() {
        return node;
    }
    
    public abstract int getAbsoluteX();
    public abstract int getAbsoluteY();
    
    public Point getAbsoluteLocation() {
        return new Point(getAbsoluteX(), getAbsoluteY());
    }
    
    public boolean isMousedOver() {
        return isMousedOver;
    }
    
    public void setMousedOver(boolean value) {
        this.isMousedOver = value;
    }
    
    public HighlightState getHighlightState() {
        return highlightState;
    }
    
    public void setHighlightState(HighlightState state) {
        this.highlightState = state;
    }
}
