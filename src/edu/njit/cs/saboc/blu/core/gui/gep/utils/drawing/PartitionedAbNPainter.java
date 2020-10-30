package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import static edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry.HighlightState.Selected;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNPainter extends AbNPainter {
        
    private PartitionedAbstractionNetwork abstractionNetwork;
        
    private int maxNodeSize = 0;

    private final Map<PartitionedNode, Integer> nodeConceptCounts = new HashMap<>();

    public PartitionedAbNPainter() {
        
    }
    
    public void initialize(PartitionedAbstractionNetwork abstractionNetwork) {
        
        this.abstractionNetwork = abstractionNetwork;
        
        int maxConceptCount = 0;
        
        Set<PartitionedNode> nodes = abstractionNetwork.getBaseAbstractionNetwork().getNodes();
        
        nodeConceptCounts.clear();
        
        for(PartitionedNode a : nodes) {
            int conceptCount = a.getConcepts().size();
            
            nodeConceptCounts.put(a, conceptCount);
            
            if(conceptCount > maxConceptCount) {
                maxConceptCount = a.getConcepts().size();
            }
        }
        
        this.maxNodeSize = maxConceptCount;
    }

    @Override
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {
        
    }

    @Override
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
                
        if (super.showingHighlights() && super.getHighlightedPartitionEntries().contains(partition.getParentContainer())) {
            g2d.setPaint(Color.RED);
            
            switch (partition.getHighlightState()) {
                case Selected:
                    if (partition.isMousedOver()) {
                        g2d.setStroke(new BasicStroke(12));
                    } else {
                        g2d.setStroke(new BasicStroke(10));
                    }

                    break;
                default:
                    if (partition.isMousedOver()) {
                        g2d.setStroke(new BasicStroke(8));
                    } else {
                        g2d.setStroke(new BasicStroke(6));
                    }
            }
        } else {
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
                        g2d.setPaint(partition.getBackground());
                    }
            }
        }

        g2d.drawRect(p.x, p.y, (int)(partition.getWidth() * scale), (int)(partition.getHeight() * scale));

        g2d.setStroke(savedStroke);
    }

    @Override
    public void paintPartitionedNodeAtPoint(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        Stroke savedStroke = g2d.getStroke();
        
        Color bgColor = entry.getBackground();
        
        Color fadedBackgroundColor = new Color(
            bgColor.getRed(),
            bgColor.getGreen(),
            bgColor.getBlue(),
            32);

        g2d.setPaint(fadedBackgroundColor);
        g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
        
        drawGrowOut(g2d, entry, p, scale);
                
        g2d.setStroke(new BasicStroke(Math.max((int)(8 * scale), 2)));
        g2d.setPaint(bgColor);
        g2d.drawRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
        
        if (super.showingHighlights() && !super.getHighlightedPartitionEntries().contains(entry)) {
            g2d.setPaint(new Color(0, 0, 0, 128));
            g2d.fillRect(p.x, p.y, (int) (entry.getWidth() * scale), (int) (entry.getHeight() * scale));
        }

        g2d.setStroke(savedStroke);
    }

    private void drawGrowOut(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        Color bgColor = entry.getBackground();

        Color sizeFillColor = new Color(
                bgColor.getRed(),
                bgColor.getGreen(),
                bgColor.getBlue(),
                200);

        g2d.setPaint(sizeFillColor);

        PartitionedNode node = entry.getPartitionedNode();

        double relativeNodeSize = (double) nodeConceptCounts.get(node) / (double) maxNodeSize;

        int drawSize = (int) (entry.getWidth() * relativeNodeSize);

        if (drawSize < entry.getWidth() / 10) {
            drawSize = entry.getWidth() / 10;
        }

        int sizeDifference = entry.getWidth() - drawSize;

        int offset = sizeDifference / 2;

        int scaledOffset = (int) (scale * offset);
        int scaledDrawSize = (int) (scale * drawSize);

        g2d.fillRect(p.x + scaledOffset, p.y + scaledOffset, scaledDrawSize, scaledDrawSize);
    }

    @Override
    public void paintMiniMapContainer(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        
        PartitionedNode node = entry.getPartitionedNode();
               
        Color baseColor = entry.getBackground();
        
        int r = baseColor.getRed();
        int g = baseColor.getGreen();
        int b = baseColor.getBlue();
        int a = 48;
        
        int scaledWidth = (int)(entry.getWidth() * scale);
        int scaledHeight = (int)(entry.getHeight()* scale);
        
        Color color = new Color(r, g, b, a);

        g2d.setPaint(color);
        g2d.fillRect(p.x, p.y, scaledWidth, scaledHeight);
        
        double relativeSize = (double)nodeConceptCounts.get(node) / (double) maxNodeSize;
        
        int relativeWidth = Math.max( (int)(scaledWidth * relativeSize), scaledWidth / 10);
        int relativeHeight = Math.max( (int)(scaledHeight * relativeSize), scaledHeight / 10);
        
        g2d.setColor(baseColor);
        
        int widthOffset = (scaledWidth - relativeWidth) / 2;
        int heightOffset = (scaledHeight - relativeHeight) / 2;
        
        g2d.fillRect(p.x + widthOffset, p.y + heightOffset, relativeWidth, relativeHeight);
        
        GenericPartitionEntry partition = entry.getPartitionEntries().iterator().next();
   
        switch (partition.getHighlightState()) {
            case Selected:
                g2d.setStroke(new BasicStroke(2));
                g2d.setPaint(Color.RED);
                
                g2d.drawRect(p.x, p.y, scaledWidth, scaledHeight);

                break;
                
            default:
                // ???
        }
    }
    
}