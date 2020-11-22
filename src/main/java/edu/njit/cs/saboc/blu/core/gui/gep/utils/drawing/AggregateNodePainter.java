package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class AggregateNodePainter {
    private static final int EDGE_RADIUS = 24;

    public static void paintGroupAtPoint(Graphics2D g2d, SinglyRootedNodeEntry nodeEntry, Point p, double scale, boolean showingHighlights, Set<SinglyRootedNodeEntry> highlightedSinglyRootedNodes, Set<PartitionedNodeEntry> highlightedPartitionNodes) {
        AggregateNode aggregateGroup = (AggregateNode)(nodeEntry.getNode());

        Color bgColor;

        switch(nodeEntry.getHighlightState()) {

            case Parent:
                bgColor = new Color(150, 150, 255);
                break;

            case Child:
                bgColor = new Color(255, 150, 255);
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

        if(nodeEntry.isMousedOver()) {
            bgColor = bgColor.brighter();
        }

        g2d.setPaint(bgColor);

        if (aggregateGroup.getAggregatedNodes().isEmpty()) {
            g2d.fillRect(p.x, p.y, (int) (nodeEntry.getWidth() * scale), (int) (nodeEntry.getHeight() * scale));
        } else {
            g2d.fillRoundRect(p.x, 
                    p.y, 
                    (int) (nodeEntry.getWidth() * scale), 
                    (int) (nodeEntry.getHeight() * scale), 
                    EDGE_RADIUS,
                    EDGE_RADIUS);
        }

        Stroke savedStroke = g2d.getStroke();

        Color outlineColor;

        if(nodeEntry.isMousedOver()) {
            g2d.setStroke(new BasicStroke(2));
            outlineColor = Color.CYAN;
        } else {
            if(nodeEntry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setStroke(new BasicStroke(1));
            }

            outlineColor = Color.BLACK;
        }

        g2d.setPaint(outlineColor);

        if (aggregateGroup.getAggregatedNodes().isEmpty()) {
            g2d.drawRect(p.x, p.y, (int) (nodeEntry.getWidth() * scale), (int) (nodeEntry.getHeight() * scale));
        } else {
            g2d.drawRoundRect(p.x, 
                    p.y, 
                    (int) (nodeEntry.getWidth() * scale), 
                    (int) (nodeEntry.getHeight() * scale), 
                    EDGE_RADIUS, 
                    EDGE_RADIUS);
        }
        
        if (showingHighlights) {

                if (highlightedSinglyRootedNodes.contains(nodeEntry)) {

                    if (nodeEntry.isMousedOver()) {
                        g2d.setStroke(new BasicStroke(2));
                    } else {
                        if (nodeEntry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                            g2d.setStroke(new BasicStroke(2));
                        } else {
                            g2d.setStroke(new BasicStroke(1));
                        }
                    }

                    g2d.setPaint(outlineColor);

                    //g2d.drawRect(p.x, p.y, (int) (nodeEntry.getWidth() * scale), (int) (nodeEntry.getHeight() * scale));

                } else {

                    if (highlightedPartitionNodes.contains(nodeEntry.getParentContainer())) {

                        if (nodeEntry.isMousedOver()) {
                            g2d.setStroke(new BasicStroke(2));
                            outlineColor = Color.CYAN;

                        } else {
                            if (nodeEntry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                                g2d.setStroke(new BasicStroke(2));
                            } else {
                                g2d.setStroke(new BasicStroke(1));
                            }

                            outlineColor = Color.BLACK;
                        }

                        g2d.setPaint(outlineColor);

                        g2d.drawRect(p.x, p.y, (int) (nodeEntry.getWidth() * scale), (int) (nodeEntry.getHeight() * scale));
                    } else {
                        g2d.setPaint(new Color(0, 0, 0, 128));
                        if (aggregateGroup.getAggregatedNodes().isEmpty()) {
                            g2d.fillRect(p.x, p.y, (int) (nodeEntry.getWidth() * scale), (int) (nodeEntry.getHeight() * scale));
                        } else {
                            g2d.fillRoundRect(p.x, 
                                    p.y, 
                                    (int) (nodeEntry.getWidth() * scale), 
                                    (int) (nodeEntry.getHeight() * scale), 
                                    EDGE_RADIUS, 
                                    EDGE_RADIUS);
                        }
                    }
                }

            } else {
            
                if (nodeEntry.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(2));
                    outlineColor = Color.CYAN;

                } else {
                    if (nodeEntry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                        g2d.setStroke(new BasicStroke(2));
                    } else {
                        g2d.setStroke(new BasicStroke(1));
                    }

                    outlineColor = Color.BLACK;
                }

                g2d.setPaint(outlineColor);
                if (aggregateGroup.getAggregatedNodes().isEmpty()) {
                    g2d.drawRect(p.x, p.y, (int) (nodeEntry.getWidth() * scale), (int) (nodeEntry.getHeight() * scale));
                } else {
                    g2d.drawRoundRect(p.x, 
                            p.y, 
                            (int) (nodeEntry.getWidth() * scale), 
                            (int) (nodeEntry.getHeight() * scale), 
                            EDGE_RADIUS, 
                            EDGE_RADIUS);
                }
            }
        g2d.setStroke(savedStroke);
    }
    
}
