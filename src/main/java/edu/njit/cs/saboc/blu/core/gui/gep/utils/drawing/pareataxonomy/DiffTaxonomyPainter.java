package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
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
public class DiffTaxonomyPainter extends AbNPainter {
    
    public void paintPartitionedNodeAtPoint(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {

        DiffArea area = (DiffArea) entry.getNode();
        
        boolean isChangedArea = false;
        
        if (area.getAreaState() == ChangeState.Introduced) {
            isChangedArea = true;
            g2d.setPaint(new Color(35, 255, 35));
        } else if (area.getAreaState() == ChangeState.Modified) {
            isChangedArea = true;
            g2d.setPaint(new Color(255, 240, 35));
        } else if (area.getAreaState() == ChangeState.Removed) {
            isChangedArea = true;
            g2d.setPaint(new Color(255, 46, 46));
        }

        if (isChangedArea) {
            int diffOffset = (int) Math.max(1.0, 8.0 * scale);

            g2d.fillRect(p.x - diffOffset, p.y - diffOffset,
                    (int) (entry.getWidth() * scale) + (2 * diffOffset),
                    (int) (entry.getHeight() * scale) + (2 * diffOffset));
        }


        Stroke savedStroke = g2d.getStroke();
        
        g2d.setPaint(Color.WHITE);
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
    
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {

        Color bgColor;

        DiffPArea parea = (DiffPArea) entry.getNode();

        if (parea.getPAreaState() == ChangeState.Introduced) {
            bgColor = new Color(220, 255, 220);
        } else if (parea.getPAreaState() == ChangeState.Modified) {
            bgColor = new Color(255, 255, 210);
        } else if (parea.getPAreaState() == ChangeState.Removed) {
            bgColor = new Color(255, 212, 212);
        } else {
            bgColor = Color.WHITE;
        }

        if (entry.isMousedOver()) {
            bgColor = bgColor.brighter();
        }

        g2d.setPaint(bgColor);

        g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));

        Stroke savedStroke = g2d.getStroke();

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
        
        if(super.showingHighlights()) {
            if (!highlightedSinglyRootedNodes.contains(entry)) {
                g2d.setPaint(new Color(0, 0, 0, 128));
                g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
            }
        }

        g2d.setStroke(savedStroke);
    }
}
