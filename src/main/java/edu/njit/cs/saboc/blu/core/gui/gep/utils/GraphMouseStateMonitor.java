package edu.njit.cs.saboc.blu.core.gui.gep.utils;

import java.awt.Point;

/**
 *
 * @author Chris
 */
public class GraphMouseStateMonitor {
    private Point currentMouseLocation = null;
    
    private Point currentDraggedLocation = null;
    
    private Point clickedLocation = null;
    
    public GraphMouseStateMonitor() {
        
    }
    
    public void setCurrentMouseLocation(Point p) {
        this.currentMouseLocation = p;
    }
    
    public void setCurrentDraggedLocation(Point p) {
        this.currentDraggedLocation = p;
    }
    
    public void setClickedLocation(Point p) {
        this.clickedLocation = p;
    }
    
    public Point getCurrentMouseLocation() {
        return currentMouseLocation;
    }
    
    public Point getCurrentDraggedLocation() {
        return currentDraggedLocation;
    }
    
    public Point getClickedLocation() {
        return clickedLocation;
    }
    
    public boolean mouseDragging() {
        return currentDraggedLocation != null;
    }
    
}
