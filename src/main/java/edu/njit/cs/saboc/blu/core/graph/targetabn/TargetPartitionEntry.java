package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class TargetPartitionEntry extends GenericPartitionEntry {
    
    public TargetPartitionEntry(int width, int height, AbstractionNetworkGraph graph, TargetContainerEntry parentBandEntry, Color c) {
        super(null, "", width, height, graph, parentBandEntry, c);
    }
    
    public TargetPartitionEntry(int width, int height, AbstractionNetworkGraph graph, TargetContainerEntry parentBandEntry, Color c, JLabel label) {
        this(width, height, graph, parentBandEntry, c);
        
        this.setLabel(label);
    }
}