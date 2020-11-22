package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import java.awt.Rectangle;

/**
 *
 * @author Chris O
 */
public class BandEntry extends PartitionedNodeEntry {
    public BandEntry(Band band, AbstractionNetworkGraph g, int aX, GraphLevel parent, Rectangle prefBounds) {
        super(band, g, aX, parent, prefBounds);
    }

    public Band getBand() {
        return (Band)super.getNode();
    }
}