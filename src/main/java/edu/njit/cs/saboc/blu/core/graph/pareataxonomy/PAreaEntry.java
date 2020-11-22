package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class PAreaEntry extends SinglyRootedNodeEntry {
    
    public PAreaEntry(PArea parea, AbstractionNetworkGraph g, RegionEntry regionEntry, int pX, GraphGroupLevel parent, ArrayList<GraphEdge> ie) {
        super(parea, g, regionEntry, pX, parent, ie);
    }
    
    public PArea getPArea() {
        return (PArea)super.getNode();
    }
}
