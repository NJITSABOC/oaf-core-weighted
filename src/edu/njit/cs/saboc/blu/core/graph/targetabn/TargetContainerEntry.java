package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import java.awt.Rectangle;

/**
 *
 * @author Chris O
 */
public class TargetContainerEntry extends PartitionedNodeEntry {
    public TargetContainerEntry(AbstractionNetworkGraph g, int aX, GraphLevel parent, Rectangle prefBounds) {
        super(null, g, aX, parent, prefBounds);
    }
}