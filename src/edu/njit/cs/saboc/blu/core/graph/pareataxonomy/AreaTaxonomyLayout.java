package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.layout.PartitionedAbNLayout;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Chris O
 */
public class AreaTaxonomyLayout extends PartitionedAbNLayout<PAreaTaxonomy> {

    public AreaTaxonomyLayout(
            AreaTaxonomyGraph graph, 
            PAreaTaxonomy taxonomy, 
            PAreaTaxonomyConfiguration config) {
        
        super(graph, taxonomy, config);
    }
    
    @Override
    protected ArrayList<Color> getLevelColors() {
        return BasePAreaTaxonomyLayout.getTaxonomyLevelColors();
    }
}
