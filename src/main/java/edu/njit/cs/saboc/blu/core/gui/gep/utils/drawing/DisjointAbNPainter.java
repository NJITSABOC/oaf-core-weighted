
package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.disjointabn.DisjointNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

/**
 *
 * @author Chris O
 */
public class DisjointAbNPainter extends AbNPainter {
    
    public void paintPartitionedNodeAtPoint(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
        
        DisjointNode node = (DisjointNode)entry.getPartitionEntries().get(0).getSubNodeEntries().get(0).getNode();
        
        if (node.getOverlaps().size() > 1) {
            g2d.setStroke(new BasicStroke(1));
            g2d.setPaint(Color.GRAY);
            g2d.drawRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
        }
        
        g2d.setStroke(savedStroke);
    }
    
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {

    }
    
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {
        
        paintOverlapBackground(g2d, entry, p, scale);

        Color bgColor;
        
        switch(entry.getHighlightState()) {
                
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
        
        if(entry.isMousedOver()) {
            bgColor = bgColor.brighter();
        }
        
        Point labelOffset = entry.getLabelOffset();
        
        int textAreaX = p.x + (int)(scale * labelOffset.x);
        int textAreaY = p.y + (int)(scale * labelOffset.y);
        
        g2d.setPaint(bgColor);
        
        g2d.fillRect(textAreaX, textAreaY, (int)(entry.getWidth() * scale), (int)(entry.getHeight()* scale));

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
        g2d.drawRect(textAreaX, textAreaY, (int)(entry.getWidth() * scale), (int)(entry.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }
    
    protected void paintOverlapBackground(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {
        
        DisjointNodeEntry disjointGroup = (DisjointNodeEntry)entry;
        
        Color [] colorSet = disjointGroup.getColorSet();
        
        int colorWidth = (int)((DisjointNodeEntry.DISJOINT_NODE_WIDTH / colorSet.length) * scale);

        int totalDrawn = 0;
        
        for (int c = 0; c < colorSet.length; c++) {
            g2d.setColor(colorSet[c]);
            
            int drawWidth;
            
            if(c < colorSet.length - 1) {
                drawWidth = colorWidth;
                totalDrawn += drawWidth;
            } else {
                drawWidth = ((int)(DisjointNodeEntry.DISJOINT_NODE_WIDTH * scale)) - totalDrawn;
            }
            
            g2d.fillRect(p.x + c * colorWidth, p.y, drawWidth, (int)(DisjointNodeEntry.DISJOINT_NODE_HEIGHT * scale));
        }
    }
}
