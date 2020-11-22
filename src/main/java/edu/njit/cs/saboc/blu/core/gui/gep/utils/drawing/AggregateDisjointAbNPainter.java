package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *
 * @author Chris O
 */
public class AggregateDisjointAbNPainter extends DisjointAbNPainter {

    @Override
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {
        super.paintOverlapBackground(g2d, entry, p, scale);

        Point savedPoint = p.getLocation();
        
        Point labelOffset = entry.getLabelOffset();
        savedPoint.translate((int)(labelOffset.x * scale), (int)(labelOffset.y * scale));

        AggregateNodePainter.paintGroupAtPoint(g2d, entry, savedPoint, scale, showingHighlights, highlightedSinglyRootedNodes, highlightedPartitionNodes);
        
        Stroke savedStroke = g2d.getStroke();
        
        Color outlineColor;
        
        if(entry.isMousedOver()) {
            g2d.setStroke(new BasicStroke(2));
            outlineColor = Color.CYAN;
        } else {
            if(entry.getHighlightState().equals(AbNNodeEntry.HighlightState.Selected)) {
                g2d.setStroke(new BasicStroke(2));
            } else {
                g2d.setStroke(new BasicStroke(1));
            }
            
            outlineColor = Color.BLACK;
        }

        g2d.setPaint(outlineColor);

        g2d.drawRect(p.x, p.y, (int)(DisjointNodeEntry.DISJOINT_NODE_WIDTH  * scale), (int)(DisjointNodeEntry.DISJOINT_NODE_HEIGHT * scale));

        g2d.setStroke(savedStroke);
    }

}
