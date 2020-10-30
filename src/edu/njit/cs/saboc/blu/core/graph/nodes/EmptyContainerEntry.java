package edu.njit.cs.saboc.blu.core.graph.nodes;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import java.awt.Rectangle;

/**
 *
 * @author Chris O
 */
public class EmptyContainerEntry extends PartitionedNodeEntry {
    public EmptyContainerEntry(AbstractionNetworkGraph g, int aX, GraphLevel parent, Rectangle prefBounds) {
        super(null, g, aX, parent, prefBounds);
    }
}
