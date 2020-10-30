package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import edu.njit.cs.saboc.blu.core.gui.gep.Viewport;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Chris O
 */
public class MinimapPanel extends AbNDisplayWidget {
    
    private final double DEFAULT_MINIMAP_ZOOM_FACTOR = 0.05;
    
    private double miniMapZoomFactor;
    
    private boolean initialized = false;
    
    private final Rectangle viewportBoxBounds = new Rectangle();
    
    private Viewport miniMapViewport;
    
    private boolean clickNavigationEnabled;
            
    private final Dimension panelSize = new Dimension(200, 150);

    public MinimapPanel(AbNDisplayPanel displayPanel) {
        super(displayPanel);
        
        this.clickNavigationEnabled = true;
                
        this.setOpaque(false);
        
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(initialized && clickNavigationEnabled) {
                    handleClickOnMinimap(e.getPoint());
                }
            }
        });
    }
    
    public void setClickNavigationEnabled(boolean enabled) {
        this.clickNavigationEnabled = enabled;
    }
    
    public void initialize(AbNDisplayPanel displayPanel) {
        this.initialized = true;
        
        this.miniMapZoomFactor = DEFAULT_MINIMAP_ZOOM_FACTOR;
        
        this.miniMapViewport = new Viewport(displayPanel.getGraph());
        this.miniMapViewport.setLocation(new Point(displayPanel.getGraph().getAbNWidth() / 2, 0));
    }
    
    public boolean initialized() {
        return this.initialized;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(!this.initialized()) {
            return;
        }
               
        final int abnRelativeWidth = (int)Math.ceil(getDisplayPanel().getGraph().getAbNWidth() * miniMapZoomFactor);
        final int abnRelativeHeight = (int)Math.ceil(getDisplayPanel().getGraph().getAbNHeight() * miniMapZoomFactor);
        
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        Graphics2D g2d = (Graphics2D)image.getGraphics();
        

        //fill the minipanel with blue background with 50% transparency.
        g2d.setColor(new Color(100, 100, 255, 32));
        g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
        
        int xOffset = 0;  //centering the x-coordinate
        
        //draw the space (in white) and its components
        g2d.setColor(Color.white);
        
        if (abnRelativeWidth < this.getWidth()) {
            xOffset = (image.getWidth() - abnRelativeWidth) / 2;
        }
        
        if (abnRelativeHeight < this.getHeight()) {
            
        }
        
        final int xDrawOffset = xOffset;
        
        g2d.fillRect(xOffset, 0, abnRelativeWidth, abnRelativeHeight);
        getDisplayPanel().getGraph().getContainerEntries().values().forEach((c) -> {

            if(miniMapViewport.getViewRegion().intersects(c.getBounds())) {
                Point location = c.getLocation();
                
                Point p = miniMapViewport.getDrawingPoint(location);
                p.x += xDrawOffset;
                
                getDisplayPanel().getAbNPainter().paintMiniMapContainer(g2d, c, p, miniMapZoomFactor);
            }
       
        });
        
        //draw viewport
        Rectangle viewportDrawBounds = viewportBoxBounds.getBounds();
        
        viewportDrawBounds.translate(xDrawOffset + 4, 4);
        viewportDrawBounds.width = viewportDrawBounds.width - 8;
        viewportDrawBounds.height = viewportDrawBounds.height - 8;
        
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(Color.ORANGE);
        g2d.draw(viewportDrawBounds);
        
        //draw border
        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(new Color(100, 100, 255));
        g2d.drawRect(0, 0, image.getWidth(), image.getHeight()); 
        
        g.drawImage(image, 0, 0, null);
    }
    
    private void handleClickOnMinimap(Point p) {

        int x = p.x;
        int y = p.y;
        
        if ((getDisplayPanel().getGraph().getAbNWidth() * miniMapZoomFactor) < this.getWidth()) {
            
            int xOffset = (this.getWidth() - (int)(getDisplayPanel().getGraph().getAbNWidth() * miniMapZoomFactor));
            
            x -= (xOffset / 2);
        }

        getDisplayPanel().getAutoScroller().snapToPointCentered(miniMapViewport.getPointOnGraph(new Point(x, y)));
    }
    
    public void update(int tick) {
        AbstractionNetworkGraph graph = getDisplayPanel().getGraph();
        Viewport userViewport = getDisplayPanel().getViewport();
        
        final int abnRelativeWidth = (int)Math.ceil(graph.getAbNWidth() * miniMapZoomFactor);
        final int abnRelativeHeight = (int)Math.ceil(graph.getAbNHeight() * miniMapZoomFactor);
               
        int regionWidth = Math.min(this.getWidth(), abnRelativeWidth);
        int regionHeight = Math.min(this.getHeight(), abnRelativeHeight);

        boolean squeezedHorizontal = false;
        
        double minimapFittingScale = DEFAULT_MINIMAP_ZOOM_FACTOR;
                
        if (userViewport.getViewRegion().width >= graph.getAbNWidth()) {
            
            double fittingScale = (double) this.getWidth() / (double) graph.getAbNWidth();

            minimapFittingScale = Math.min(minimapFittingScale, fittingScale);

            squeezedHorizontal = true;
        } 
        
        boolean squeezedVertical = false;
        
        if(userViewport.getViewRegion().height >= graph.getAbNHeight()) {
            double fittingScale = (double)this.getHeight() / (double)graph.getAbNHeight();
            
            minimapFittingScale = Math.min(minimapFittingScale, fittingScale);
            
            squeezedVertical = true;
        }
        
        this.miniMapZoomFactor = minimapFittingScale;
        
        this.miniMapViewport.setParentFrameSize(new Dimension(regionWidth, regionHeight));
        this.miniMapViewport.forceZoom((int)(this.miniMapZoomFactor * 100));
        
        int miniMapXStart = this.miniMapViewport.getViewRegion().x;
        int miniMapXEnd = miniMapXStart + this.miniMapViewport.getViewRegion().width;
        
        int miniMapYStart = this.miniMapViewport.getViewRegion().y;
        int miniMapYEnd = miniMapYStart + this.miniMapViewport.getViewRegion().height;
        
        int userXStart = userViewport.getViewRegion().x;
        int userXEnd = userXStart + userViewport.getViewRegion().width;
        
        int userYStart = userViewport.getViewRegion().y;
        int userYEnd = userYStart + userViewport.getViewRegion().height;
        
        if (squeezedHorizontal) {
            
            this.miniMapViewport.getViewRegion().y = Math.max(0, userYStart);

        } else {
            if (miniMapViewport.getViewRegion().width > userViewport.getViewRegion().width) {
                if (userXEnd > miniMapXEnd) {
                    this.miniMapViewport.getViewRegion().x += userXEnd - miniMapXEnd;

                } else if (userXStart < miniMapXStart) {
                    this.miniMapViewport.getViewRegion().x = userXStart;
                }
            } else {
                this.miniMapViewport.getViewRegion().x = userXStart;
            }
        }

        if (squeezedVertical) {
            int offset = miniMapViewport.getViewRegion().width - userViewport.getViewRegion().width;
            
            this.miniMapViewport.getViewRegion().x = Math.max(0, userXStart - offset / 2);
        } else {
            if (miniMapViewport.getViewRegion().height > userViewport.getViewRegion().height) {
                
                if (userYEnd > miniMapYEnd) {
                    this.miniMapViewport.getViewRegion().y += userYEnd - miniMapYEnd;
                } else if (userYStart < miniMapYStart) {
                    this.miniMapViewport.getViewRegion().y = userYStart;
                }
                
            } else {
                this.miniMapViewport.getViewRegion().y = userYStart;
            }
        }

        int currentX = userViewport.getViewRegion().x;
        int currentY = userViewport.getViewRegion().y;
        
        int currentWidth = userViewport.getViewRegion().width;
        int currentHeight = userViewport.getViewRegion().height;
        
        int drawWidth = (int)Math.ceil(currentWidth * miniMapZoomFactor);
        int drawHeight = (int)Math.ceil(currentHeight * miniMapZoomFactor);
        
        if(drawWidth > this.getWidth()) {
            drawWidth = this.getWidth();
        }
        
        if(drawHeight > this.getHeight()) {
            drawHeight = this.getHeight();
        }

        if (userViewport.getViewRegion().width > graph.getAbNWidth()) {
            this.viewportBoxBounds.x = 0;
            this.viewportBoxBounds.width = Math.min(abnRelativeWidth, this.getWidth());
        } else {
            this.viewportBoxBounds.width = drawWidth;

            if (abnRelativeWidth <= this.getWidth()) {
                if (drawWidth == this.getWidth()) {
                    viewportBoxBounds.x = 0;
                } else {
                    viewportBoxBounds.x = (int) (currentX * miniMapZoomFactor);
                }
            } else {
                viewportBoxBounds.x = (int) ((userViewport.getViewRegion().x - this.miniMapViewport.getViewRegion().x) * miniMapZoomFactor);
            }

            if (viewportBoxBounds.x < 0) {
                viewportBoxBounds.x = 0;
            }
        }

        if (userViewport.getViewRegion().height >= graph.getAbNHeight()) {
            this.viewportBoxBounds.y = 0;
            this.viewportBoxBounds.height = Math.min(abnRelativeHeight, this.getHeight());
        } else {
            this.viewportBoxBounds.height = drawHeight;
            
            if (abnRelativeHeight <= this.getHeight()) {
                if (drawHeight == this.getHeight()) {
                    viewportBoxBounds.y = this.getHeight();
                } else {
                    viewportBoxBounds.y = (int) (currentY * miniMapZoomFactor);
                }
            } else {
                viewportBoxBounds.y = (int) ((userViewport.getViewRegion().y - this.miniMapViewport.getViewRegion().y) * miniMapZoomFactor);
            }
            
            if(viewportBoxBounds.y < 0) {
                viewportBoxBounds.y = 0;
            }
        }
    }

    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        super.displayPanelResized(displayPanel);
        
        this.setBounds(displayPanel.getWidth() - panelSize.width - 20, 10, panelSize.width, panelSize.height);
    }
}
