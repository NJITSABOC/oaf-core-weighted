package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import javax.swing.JLabel;

/**
 * Entry representing the splitting of a Band based on intersection inheritance 
 * (e.g., like Regions in a partial-area taxonomy). For now this is just used
 * for consistency in graph representation.
 * 
 * @author Chris O
 */
public class BandPartitionEntry extends GenericPartitionEntry {
    
    public BandPartitionEntry(Band band, String bandName, int width, int height, AbstractionNetworkGraph graph, BandEntry parentBandEntry, Color c) {
        super(band, bandName, width, height, graph, parentBandEntry, c);
    }
    
    public BandPartitionEntry(Band band, String bandName,
            int width, int height, AbstractionNetworkGraph graph, BandEntry parentBandEntry, Color c, JLabel label) {

        super(band, bandName, width, height, graph, parentBandEntry, c);
        
        this.remove(partitionLabel);
        
        this.partitionLabel = label;

        this.add(label);
    }
}