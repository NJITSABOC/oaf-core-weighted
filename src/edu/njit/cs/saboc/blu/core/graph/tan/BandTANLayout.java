package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.layout.PartitionedAbNLayout;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.BasePAreaTaxonomyLayout;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class BandTANLayout extends PartitionedAbNLayout<ClusterTribalAbstractionNetwork> {

    public BandTANLayout(
            AbstractionNetworkGraph graph,
            ClusterTribalAbstractionNetwork taxonomy,
            TANConfiguration config) {

        super(graph, taxonomy, config);
    }

    @Override
    protected ArrayList<Color> getLevelColors() {
        return BasePAreaTaxonomyLayout.getTaxonomyLevelColors();
    }
}
