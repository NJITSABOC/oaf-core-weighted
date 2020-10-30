package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry.HighlightState;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris
 */
public class AbNPainter {
    
    protected final Set<SinglyRootedNodeEntry> highlightedSinglyRootedNodes = new HashSet<>();
    protected final Set<PartitionedNodeEntry> highlightedPartitionNodes = new HashSet<>();
    
    protected boolean showingHighlights = false;
    
    public void clearHighlights() {
        highlightedSinglyRootedNodes.clear();
        highlightedPartitionNodes.clear();
        
        showingHighlights = false;
    }
    
    public void setHighlightedSinglyRootedNodes(Set<SinglyRootedNodeEntry> entries) {
        clearHighlights();
        
        highlightedSinglyRootedNodes.addAll(entries);
        
        showingHighlights = true;
    }
    
    public void setHighlightedPartitionNodes(Set<PartitionedNodeEntry> partitionNodes) {
        clearHighlights();
        
        highlightedPartitionNodes.addAll(partitionNodes);
        
        showingHighlights = true;
    }
    
    public boolean showingHighlights() {
        return showingHighlights;
    }
    
    protected boolean singlyRootedNodeHighlighted(SinglyRootedNodeEntry entry) {
        return showingHighlights && highlightedSinglyRootedNodes.contains(entry);
    }
    
    protected boolean partitionedNodeHighlighted(PartitionedNodeEntry entry) {
        return showingHighlights && highlightedPartitionNodes.contains(entry);
    }
    
    public Set<SinglyRootedNodeEntry> getHighlightedSinglyRootedNodeEntries() {
        return highlightedSinglyRootedNodes;
    }
    
    public Set<PartitionedNodeEntry> getHighlightedPartitionEntries() {
        return highlightedPartitionNodes;
    }
    
    public void paintPartitionedNodeAtPoint(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
        
        g2d.setPaint(entry.getBackground());
        g2d.fillRect(p.x, p.y, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale));
        
        g2d.setStroke(new BasicStroke(2));
        g2d.setPaint(Color.BLACK);
        g2d.drawRect(p.x, p.y, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale));
        
        if (showingHighlights && !partitionedNodeHighlighted(entry)) {
            g2d.setPaint(new Color(0, 0, 0, 128));
            g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
        }
        
        g2d.setStroke(savedStroke);
    }
    
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();

        if (showingHighlights && partitionedNodeHighlighted(partition.getParentContainer())) {
            
            g2d.setPaint(Color.RED);

            if (partition.getHighlightState() == HighlightState.Selected) {
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(4));
                } else {
                    g2d.setStroke(new BasicStroke(2));
                }

            } else {
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    g2d.setStroke(new BasicStroke(1));
                }
            }
            
        } else {

            if (partition.getHighlightState() == HighlightState.Selected) {
                
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(5));
                    g2d.setPaint(Color.YELLOW);
                } else {
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setPaint(Color.YELLOW);
                }
            } else {
                
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setPaint(Color.CYAN);
                } else {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setPaint(Color.BLACK);
                }
            }
        }
        
        g2d.drawRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }
    
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {
        
        Color bgColor;
        
        switch(entry.getHighlightState()) {
                
            case Parent:
                bgColor = new Color(200, 200, 255);
                break;
                
            case Child:
                bgColor = new Color(255, 200, 255);
                break;
                
            case Selected:
                bgColor = new Color(255, 255, 100);
                break;
                
            case SearchResult:
                bgColor = new Color(255, 150, 150);
                break;
                
            default:
                bgColor = Color.WHITE;
        }
        
        Stroke savedStroke = g2d.getStroke();
        
        if (entry.isMousedOver()) {
            bgColor = bgColor.brighter();
        }

        g2d.setPaint(bgColor);

        g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));

        if (showingHighlights) {

            if (highlightedSinglyRootedNodes.contains(entry)) {

                Color outlineColor = Color.RED;

                if (entry.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    if (entry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                        g2d.setStroke(new BasicStroke(2));
                    } else {
                        g2d.setStroke(new BasicStroke(1));
                    }
                }

                g2d.setPaint(outlineColor);

                g2d.drawRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));

            } else {

                if (highlightedPartitionNodes.contains(entry.getParentContainer())) {
                    Color outlineColor;

                    if (entry.isMousedOver()) {
                        g2d.setStroke(new BasicStroke(2));
                        outlineColor = Color.CYAN;

                    } else {
                        if (entry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                            g2d.setStroke(new BasicStroke(2));
                        } else {
                            g2d.setStroke(new BasicStroke(1));
                        }

                        outlineColor = Color.BLACK;
                    }

                    g2d.setPaint(outlineColor);

                    g2d.drawRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
                } else {
                    g2d.setPaint(new Color(0, 0, 0, 128));
                    g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
                }
            }

        } else {
            Color outlineColor;

            if (entry.isMousedOver()) {
                g2d.setStroke(new BasicStroke(2));
                outlineColor = Color.CYAN;
                
            } else {
                if (entry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                    g2d.setStroke(new BasicStroke(2));
                } else {
                    g2d.setStroke(new BasicStroke(1));
                }

                outlineColor = Color.BLACK;
            }

            g2d.setPaint(outlineColor);

            g2d.drawRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
        }

        g2d.setStroke(savedStroke);
    }

    public void paintMiniMapContainer(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        g2d.setPaint(entry.getBackground());
        
        g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
    }
}
