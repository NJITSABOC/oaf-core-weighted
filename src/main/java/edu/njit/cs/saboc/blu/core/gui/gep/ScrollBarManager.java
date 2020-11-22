
package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Chris O
 */
public class ScrollBarManager implements UpdateableAbNDisplayEntity {
    
    private final int SCROLLER_SIZE = 10;
    
    private AbNDisplayPanel displayPanel;
    
    private final Rectangle xScrollerBounds = new Rectangle();
    private final Rectangle yScrollerBounds = new Rectangle();
    
    private int xScrollerClickOffset = 0;
    private int ySCrollerClickOffset = 0;
    
    private boolean initialized = false;
    
    private boolean xScrollerPressed = false;
    private boolean yScrollerPressed = false;
    
    public ScrollBarManager() {

    }

    public void initialize(AbNDisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
        
        xScrollerBounds.height = SCROLLER_SIZE;
        yScrollerBounds.width = SCROLLER_SIZE;
        
        this.xScrollerClickOffset = 0;
        this.ySCrollerClickOffset = 0;
        
        this.initialized = true;
    }
    
    public boolean isInitalized() {
        return initialized;
    }
    
    public void update(int tick) {
        
        if(!initialized) {
            return;
        }
        
        Viewport viewport = displayPanel.getViewport();
        
        Rectangle viewportRegion = viewport.getViewRegion();
        AbstractionNetworkGraph graph = displayPanel.getGraph();
        
        int xStartPoint = (int)(((double)viewportRegion.x / graph.getAbNWidth()) * displayPanel.getWidth());
        int xEndPoint = xStartPoint + (int)(((double)viewportRegion.width / graph.getAbNWidth()) * displayPanel.getWidth());
        
        int yStartPoint = (int)(((double)viewportRegion.y / graph.getAbNHeight()) * displayPanel.getHeight());
        int yEndPoint = yStartPoint + (int)(((double)viewportRegion.height / graph.getAbNHeight()) * displayPanel.getHeight());
        
        xScrollerBounds.x = xStartPoint;
        xScrollerBounds.y = displayPanel.getHeight() - xScrollerBounds.height;
        xScrollerBounds.width = xEndPoint - xStartPoint;
        
        yScrollerBounds.y = yStartPoint;
        yScrollerBounds.x = displayPanel.getWidth() - yScrollerBounds.width;
        yScrollerBounds.height = yEndPoint - yStartPoint;
    }
    
    public boolean xScrollerContainsPoint(Point p) {
        return xScrollerBounds.contains(p);
    }
    
    public boolean yScrollerContainsPoint(Point p) {
        return yScrollerBounds.contains(p);
    }
    
    public boolean scrollerContainsPoint(Point p) {
        return xScrollerContainsPoint(p) || yScrollerContainsPoint(p);
    }
    
    public void setXScrollerPressed(int absoluteX, boolean value) {
        this.xScrollerPressed = value;
        this.xScrollerClickOffset = xScrollerBounds.x - absoluteX;
    }
    
    public void setXScrollerPressed(boolean value) {
        setXScrollerPressed(0, value);
    }
    
    public void setYScrollerPressed(int absoluteY, boolean value) {
        this.yScrollerPressed = value;
        this.ySCrollerClickOffset = yScrollerBounds.y - absoluteY;
    }
    
    public void setYScrollerPressed(boolean value) {
        setYScrollerPressed(0, value);
    }
    
    public void setNoScrollerPressed() {
        setXScrollerPressed(false);
        setYScrollerPressed(false);
    }
    
    public boolean scrollerPressed() {
        return xScrollerPressed() || yScrollerPressed();
    }
    
    public boolean xScrollerPressed() {
        return this.xScrollerPressed;
    }
    
    public boolean yScrollerPressed() {
        return this.yScrollerPressed;
    }
    
    public int getXScrollerClickLocationOffset() {
        return this.xScrollerClickOffset;
    }
    
    public int getYScrollerClickLocationOffset() {
        return this.ySCrollerClickOffset;
    }
    
    public Rectangle getXScrollerBounds() {
        return xScrollerBounds;
    }
    
    public Rectangle getYScrollerBounds() {
        return yScrollerBounds;
    }
}
