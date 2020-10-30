package edu.njit.cs.saboc.blu.core.gui.gep;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Chris
 */
public class Viewport {

    private Rectangle region = new Rectangle(0, 0, 0, 0);
    
    private double scale = 1.0;
    
    private int zoomLevel = 100;

    private final AbstractionNetworkGraph graph;
    
    private Dimension parentFrameSize;

    public Viewport(AbstractionNetworkGraph graph) {
        this.graph = graph;
        this.parentFrameSize = new Dimension(0, 0);
    }
    
    public AbstractionNetworkGraph getGraph() {
        return graph;
    }
    
    public Rectangle getViewRegion() {
        return region;
    }
    
    public double getViewScale() {
        return scale;
    }
    
    /**
     * Returns the zoom level as a integer percent
     * @return 
     */
    public int getZoomFactor() {
        return zoomLevel;
    }
    
    public void setSizeAbsolute(int width, int height) {
        region.setSize(width, height);
    }
    
    public void setSizeScaled(int width, int height) {
        setSizeAbsolute((int)(width / scale), (int)(height / scale));
    }
    
    public void setParentFrameSize(Dimension parentFrameSize) {
        this.parentFrameSize = parentFrameSize;
    }
    
    public Point getScaledPoint(Point point) {
        return new Point((int)(point.x * scale), (int)(point.y * scale));
    }
    
    public Point getScaledSize(int width, int height) {
        return new Point((int)(width * scale), (int)(height * scale));
    }
    
    public Point getDrawingPoint(Point point) {
        int x = (int)Math.floor((point.x - region.x) * scale);
        int y = (int)Math.floor((point.y - region.y) * scale);
        
        return new Point(x, y);
    }
    
    public void setLocation(Point point) {
        
        // Max size locks horizontal movement. Better option is to maintain this in some kind of state variable
        if(region.width == graph.getAbNWidth()) {
            region.y = point.y;
            
            return;
        }
        
        region.setLocation(point);
    }
    
    public void moveHorizontal(int distance) {
        
        // Max size locks horizontal movement
        if(region.width >= graph.getAbNWidth()) {
            return;
        }
        
        region.x += distance;

        if(region.x < 0) {
            region.x = 0;
        } else if(region.x + region.width > graph.getAbNWidth()) {
            region.x = Math.max(graph.getAbNWidth() - region.width, 0);
        }
    }

    public void moveVertical(int distance) {
        region.y += distance;

        if(region.y < 0) {
            region.y = 0;
        } else if(region.y + region.height > graph.getAbNHeight()) {
            region.y = Math.max(graph.getAbNHeight() - region.height, 0);
        }
    }
    
    public void move(Point distance) {
        moveHorizontal(distance.x);
        moveVertical(distance.y);
    }
    
    public void moveScaled(Point distance) {
        move(new Point((int)(distance.x / scale), (int)(distance.y / scale)));
    }

    public void moveHorizontalScaled(int distance) {
        moveHorizontal((int)(distance / scale));
    }

    public void moveVerticalScaled(int distance) {
        moveVertical((int)(distance / scale));
    }
    
    public void forceZoom(int percent) {
        this.zoomLevel = percent;
        this.scale = percent / 100.0;
        
        int midPointX = region.x + (region.width / 2);
        int midPointY = region.y + (region.height / 2);

        region.width = (int)(this.parentFrameSize.width / scale);
        region.height = (int)(this.parentFrameSize.height / scale);

        if(region.height > graph.getAbNHeight()) {
            region.height = graph.getAbNHeight();
        }

        if(region.width > graph.getAbNWidth()) {

            region.width = graph.getAbNWidth();
            
            int x = this.parentFrameSize.width - (int)(region.width * scale);
            
            x /= 2;
            
            region.x = -(int)(x / scale);
        } else {
            region.x = midPointX - region.width / 2;

            if (region.x < 0) {
                region.x = 0;
            } else if (region.x + region.getWidth() > graph.getAbNWidth()) {
                region.x = graph.getAbNWidth() - (int) region.getWidth();
            }
        }
        
        region.y = midPointY - region.height / 2;

        if(region.y < 0) {
            region.y = 0;
        } else if(region.y + region.getHeight() > graph.getAbNHeight()) {
            region.y = graph.getAbNHeight() - (int)region.getHeight();
        }
    }
    
    public void setZoomChecked(int percent) {
        
        if(percent < 10) {
            percent = 10;
        } else if(percent > 200) {
            percent = 200;
        }
        
        forceZoom(percent);
    }
    
    public Point getPointOnGraph(Point clicked) {
        int x = region.x + (int) (clicked.x / scale);
        int y = region.y + (int) (clicked.y / scale);
        
        return new Point(x, y);
    }

    public void focusOnPoint(int x, int y) {
        this.setZoomChecked(100);

        int xView = x - (region.width / 2);
        int yView = y - (region.height / 2);

        if (xView < 0) {
            xView = 0;
        } else if (xView > (graph.getAbNWidth() - region.width)) {
            xView = graph.getAbNWidth() - region.width;
        }

        if (yView < 0) {
            yView = 0;
        } else if (yView > (graph.getAbNHeight() - region.height)) {
            yView = graph.getAbNHeight() - region.height;
        }
        
        region.setLocation(xView, yView);
    }
}
