package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.target;

import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Chris O
 */
public class TargetAbNPainter extends AbNPainter {

    @Override
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry entry, Point p, double scale) {
        super.paintSinglyRootedNodeAtPoint(g2d, entry, p, scale);
    }

    @Override
    public void paintPartitionAtPoint(Graphics2D g2d, GenericPartitionEntry partition, Point p, double scale) {
        
    }

    @Override
    public void paintPartitionedNodeAtPoint(Graphics2D g2d, PartitionedNodeEntry entry, Point p, double scale) {
        
    }
}
