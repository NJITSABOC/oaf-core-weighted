package edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.pareataxonomy.NoRegionsPAreaTaxonomyLayout;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomySubviewLayout extends NoRegionsPAreaTaxonomyLayout<DiffPAreaTaxonomy> {
    
    public DiffPAreaTaxonomySubviewLayout(
            DiffPAreaTaxonomyGraph graph,
            DiffPAreaTaxonomy taxonomy,
            DiffPAreaTaxonomyConfiguration config,
            DiffTaxonomySubsetOptions subsetOptions) {
        
        super(graph, 
                taxonomy, 
                config, 
                new DiffAreaInclusionTester(taxonomy, subsetOptions.getAllowedDiffAreaStates()),
                new DiffPAreaInclusionTester(taxonomy, subsetOptions.getAllowedDiffPAreaStates()));
    }
    
}
