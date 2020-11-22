package edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.graph.nodes.AbNNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNPainter;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AggregateNodePainter; 
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
public class AggregatePAreaTaxonomyPainter extends AbNPainter {
    private static final int EDGE_RADIUS = 24;  
    
    @Override
    public void paintSinglyRootedNodeAtPoint(Graphics2D g2d, SinglyRootedNodeEntry nodeEntry, Point p, double scale) {
        AggregateNodePainter.paintGroupAtPoint(g2d, nodeEntry, p, scale, showingHighlights, highlightedSinglyRootedNodes, highlightedPartitionNodes);
    }
}
