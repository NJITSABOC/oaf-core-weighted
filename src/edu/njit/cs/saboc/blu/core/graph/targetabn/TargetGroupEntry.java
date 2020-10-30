package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class TargetGroupEntry extends SinglyRootedNodeEntry {
    public TargetGroupEntry(TargetGroup targetGroup, 
            AbstractionNetworkGraph g, 
            TargetPartitionEntry emptyPartition, 
            int pX, 
            GraphGroupLevel parent, 
            ArrayList<GraphEdge> ie) {
        
        super(targetGroup, g, emptyPartition, pX, parent, ie);
    }

    public TargetGroup getTargetGroup() {
        return (TargetGroup)super.getNode();
    }
}