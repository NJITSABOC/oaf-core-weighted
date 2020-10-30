package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class ClusterEntry extends SinglyRootedNodeEntry {
    public ClusterEntry(Cluster cluster, 
            AbstractionNetworkGraph g, 
            BandPartitionEntry emptyPartition, 
            int pX, 
            GraphGroupLevel parent, 
            ArrayList<GraphEdge> ie) {
        
        super(cluster, g, emptyPartition, pX, parent, ie);
    }

    public Cluster getCluster() {
        return (Cluster)super.getNode();
    }
}
