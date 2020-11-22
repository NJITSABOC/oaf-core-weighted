package edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.BasePAreaTaxonomyLayout;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyGraph;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.PAreaTaxonomyLayoutFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomySubviewLayoutFactory implements PAreaTaxonomyLayoutFactory<DiffPAreaTaxonomy> {
    
    private final DiffTaxonomySubsetOptions subsetOptions;
    
    public DiffPAreaTaxonomySubviewLayoutFactory(DiffTaxonomySubsetOptions subsetOptions) {
        this.subsetOptions = subsetOptions;
    }

    @Override
    public BasePAreaTaxonomyLayout createLayout(
            PAreaTaxonomyGraph graph, 
            DiffPAreaTaxonomy taxonomy, 
            PAreaTaxonomyConfiguration config) {
        
        return new DiffPAreaTaxonomySubviewLayout( 
                (DiffPAreaTaxonomyGraph)graph, 
                taxonomy, 
                (DiffPAreaTaxonomyConfiguration)config, 
                subsetOptions);
        
    }
}
