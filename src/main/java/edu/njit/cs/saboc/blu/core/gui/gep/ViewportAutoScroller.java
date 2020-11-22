package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class ViewportAutoScroller implements UpdateableAbNDisplayEntity {
    
    private Optional<Point> targetPoint = Optional.empty();
    
    private Viewport viewport;
    
    public ViewportAutoScroller() {
        
    }
    
    private void setViewport(Viewport viewport) {
        this.viewport = viewport;
        this.targetPoint = Optional.empty();
    }
    
    public void navigateToPoint(Point thePoint) {
        this.targetPoint = Optional.of(thePoint);
    }
    
    public void cancelAutoNavigation() {
        this.targetPoint = Optional.empty();
    }

    @Override
    public void initialize(AbNDisplayPanel displayPanel) {
        this.setViewport(displayPanel.getViewport());
        
        resetDisplay();
    }
    
    public void resetDisplay() {
        int xMidpoint = viewport.getGraph().getAbNWidth() / 2 - viewport.getViewRegion().width / 2;
        
        this.snapToPoint(new Point(xMidpoint, 0));
    }
    
    public void autoNavigateToNodeEntry(AbNNodeEntry entry) {

        double viewportScale = viewport.getViewScale();
        
        if (viewportScale > 0.6) {
            targetPoint = Optional.of(getNodeEntryNavigationPoint(entry));
        }
    }
    
    public void snapToPoint(Point point) {
        viewport.setLocation(point);
    }
    
    public void snapToPointCentered(Point point) {
        int xCentered = point.x - (viewport.getViewRegion().width / 2);
        int yCentered = point.y - (viewport.getViewRegion().height / 2);
        
        this.snapToPoint(new Point(xCentered, yCentered));
    }
    
    public void snapToNodeEntry(AbNNodeEntry entry) {
        snapToPoint(getNodeEntryNavigationPoint(entry));
    }
    
    private Point getNodeEntryNavigationPoint(AbNNodeEntry entry) {
        int entryXPos = entry.getAbsoluteX();
        int entryYPos = entry.getAbsoluteY();

        Rectangle viewportRegion = viewport.getViewRegion();
        double viewportScale = viewport.getViewScale();

        int xBufferArea = (int) (entry.getWidth() * viewportScale * 2);
        int yBufferArea = (int) (entry.getHeight() * viewportScale * 2);

        int destX = viewportRegion.x;
        int destY = viewportRegion.y;

        int graphWidth = viewport.getGraph().getAbNWidth();
        int graphHeight = viewport.getGraph().getAbNHeight();

        if (entryXPos + entry.getWidth() <= viewportRegion.x + xBufferArea
                || entryXPos >= viewportRegion.x + viewportRegion.width - xBufferArea) {

            destX = entry.getAbsoluteX() + entry.getWidth() / 2 - viewportRegion.width / 2;

            if (destX < 0) {
                destX = 0;
            } else if (destX > graphWidth - viewportRegion.width) {
                destX = graphWidth - viewportRegion.width;
            }
        }

        if (entryYPos + entry.getHeight() <= viewportRegion.y + yBufferArea
                || entryYPos >= viewportRegion.y + viewportRegion.height - yBufferArea) {

            destY = entry.getAbsoluteY() + entry.getHeight() / 2 - viewportRegion.height / 2;

            if (destY < 0) {
                destY = 0;
            } else if (destY > graphHeight - viewportRegion.height) {
                destY = graphHeight - viewportRegion.height;
            }
        }

        return new Point(destX, destY);
    }
    
    public void update(int tick) {
        if (targetPoint.isPresent()) {
            Point thePoint = targetPoint.get();

            Rectangle viewportRegion = viewport.getViewRegion();

            int dx = thePoint.x - viewportRegion.x;
            int dy = thePoint.y - viewportRegion.y;

            final int MOVE_SPEED = 196;
            final int CLOSEST_DISTANCE = 128;

            int distSquared = dx * dx + dy * dy;

            if (Math.sqrt(distSquared) <= CLOSEST_DISTANCE) {
                viewport.setLocation(thePoint);
                
                targetPoint = Optional.empty();
            } else {
                double angle = Math.atan2(dy, dx);
                viewport.moveScaled(new Point((int) (MOVE_SPEED * Math.cos(angle)), (int) (MOVE_SPEED * Math.sin(angle))));
            }
        }
    }
}
