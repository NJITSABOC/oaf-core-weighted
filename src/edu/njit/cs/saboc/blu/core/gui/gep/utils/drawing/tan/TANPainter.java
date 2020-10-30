package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.tan;

import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *
 * @author Chris O
 */
public class TANPainter extends AbNPainter {
    
    public void paintPartitionedNodeAtPoint(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
        
        final int EDGE_RADIUS = 64;
        
        int radius = Math.max(1, (int)(EDGE_RADIUS * scale));
        
        g2d.setPaint(entry.getBackground());
        g2d.fillRoundRect(p.x, p.y, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale), radius, radius);
        
        int strokeWidth = Math.max(1, (int)(3 * scale));
        
        Stroke dashedStroke = new BasicStroke(strokeWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        
        g2d.setStroke(dashedStroke);
        g2d.setPaint(Color.BLACK);
        g2d.drawRoundRect(p.x, p.y, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale), radius, radius);
        
        g2d.setStroke(savedStroke);
    }
    
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
        
        final int EDGE_RADIUS = 64;
        
        int radius = Math.max(1, (int)(EDGE_RADIUS * scale));

        g2d.setColor(partition.getBackground());
        
        if (showingHighlights) {
            g2d.setPaint(new Color(0, 0, 0, 128));
            g2d.fillRoundRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale), radius, radius);
        } else {        
            g2d.fillRoundRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale), radius, radius);
        }
        
        switch (partition.getHighlightState()) {
            case Selected:
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(5));
                    g2d.setPaint(Color.YELLOW);
                } else {
                    g2d.setStroke(new BasicStroke(3));
                    g2d.setPaint(Color.YELLOW);
                }

                break;
            default:
                if (partition.isMousedOver()) {
                    g2d.setStroke(new BasicStroke(2));
                    g2d.setPaint(Color.CYAN);
                } else {
                    g2d.setStroke(new BasicStroke(1));
                    g2d.setPaint(Color.BLACK);
                }
        }

        g2d.drawRoundRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale), radius, radius);

        g2d.setStroke(savedStroke);
    }
}
