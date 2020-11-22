package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing;

import edu.njit.cs.saboc.blu.core.gui.gep.Viewport;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public class AbNDrawingUtilities {
    public static void paintContainer(AbNPainter painter, Graphics2D g2d, PartitionedNodeEntry container, Viewport viewport, AbNLabelManager labelManager) {

        Point p = viewport.getDrawingPoint(new Point(container.getLocation()));

        painter.paintPartitionedNodeAtPoint(g2d, container, p, viewport.getViewScale());

        Component [] components = container.getComponents();

        for (Component component : components) {
            if (component.isVisible()) {
                if (component instanceof GenericPartitionEntry) {
                    paintPartition(painter, g2d, ((GenericPartitionEntry) component), viewport, labelManager);
                } else if (component instanceof JLabel) {
                    JLabel label = ((JLabel) component);

                    Point labelLocation = label.getLocation();

                    Point drawPoint = viewport.getDrawingPoint(new Point(container.getX() + labelLocation.x, container.getY() + labelLocation.y));

                    labelManager.drawLabel(g2d, label, viewport.getViewScale(), drawPoint.x, drawPoint.y);
                }
            }
        }
    }

    public static void paintPartition(AbNPainter painter, Graphics2D g2d, GenericPartitionEntry partition, Viewport viewport, AbNLabelManager labelManager) {
        Point p = viewport.getDrawingPoint(new Point(partition.getAbsoluteX(), partition.getAbsoluteY()));
        
        painter.paintPartitionAtPoint(g2d, partition, p, viewport.getViewScale());
        
        Component [] components = partition.getComponents();

        for(Component component : components) {
            if(component instanceof SinglyRootedNodeEntry) {
                paintGroupEntry(painter, g2d, (SinglyRootedNodeEntry)component, viewport, labelManager);
                
                
            } else if(component instanceof JLabel) {
                JLabel label = ((JLabel) component);
                
                Point labelLocation = label.getLocation();

                Point drawPoint = viewport.getDrawingPoint(new Point(partition.getAbsoluteX() + labelLocation.x, partition.getAbsoluteY() + labelLocation.y));

                labelManager.drawLabel(g2d, label, viewport.getViewScale(), drawPoint.x, drawPoint.y);
                
            } else {
                // What else could be drawing?
            }
        }
    }
    
    public static void paintGroupEntry(AbNPainter painter, Graphics2D g2d, SinglyRootedNodeEntry group, Viewport viewport, AbNLabelManager labelManager) {
        
        Point p = viewport.getDrawingPoint(new Point(group.getAbsoluteX(), group.getAbsoluteY()));

        painter.paintSinglyRootedNodeAtPoint(g2d, group, p, viewport.getViewScale());

        Component[] components = group.getComponents();
        JLabel label = ((JLabel) components[0]);

        Font savedFont = g2d.getFont();

        g2d.setFont(label.getFont());
        
        Point labelOffset = group.getLabelOffset();
        
        
        labelManager.drawGroupLabel(
                group.getNode(), 
                viewport.getViewScale(), 
                g2d, 
                p.x + (int)(viewport.getViewScale() * labelOffset.x), 
                p.y + (int)(viewport.getViewScale() * labelOffset.y));
        
        g2d.setFont(savedFont);
    }

    public static void paintEdge(Graphics2D g2d, GraphEdge edge, Viewport viewport) {
        g2d.setColor(edge.getSegments().get(0).getBackground());
        
        for(JPanel segment : edge.getSegments()) {
            if(viewport.getViewRegion().intersects(segment.getBounds())) {
                Point p = viewport.getDrawingPoint(segment.getLocation());
                
                int width = (int)(segment.getWidth() * viewport.getViewScale());
                int height = (int)(segment.getHeight() * viewport.getViewScale());
                
                g2d.fillRect(p.x, p.y, width, height);
            }
        }
    }
}
