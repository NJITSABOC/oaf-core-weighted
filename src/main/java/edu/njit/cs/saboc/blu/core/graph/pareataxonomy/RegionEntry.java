package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Region;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class RegionEntry extends GenericPartitionEntry {
    
    public RegionEntry(Region region, String regionName, int width, int height, AbstractionNetworkGraph g, AreaEntry area, Color c) {
        super(region, regionName, width, height, g, area, c);
    }
    
    public RegionEntry(Region region, String regionName,
            int width, int height, AbstractionNetworkGraph g, AreaEntry p, Color c, JLabel label) {

        super(region, regionName, width, height, g, p, c);
        
        this.remove(partitionLabel);
        
        this.partitionLabel = label;

        this.add(label);
    }
    
    public Region getRegion() {
        return (Region)super.getNode();
    }
}
