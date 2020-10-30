package edu.njit.cs.saboc.blu.core.graph.nodes;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;

/**
 *
 * @author Chris O
 */
public class EmptyContainerPartitionEntry extends GenericPartitionEntry {
    public EmptyContainerPartitionEntry(int width, int height, PartitionedNodeEntry parent, AbstractionNetworkGraph g) {
        super(null, "", width, height, g, parent, null);
    }
}
