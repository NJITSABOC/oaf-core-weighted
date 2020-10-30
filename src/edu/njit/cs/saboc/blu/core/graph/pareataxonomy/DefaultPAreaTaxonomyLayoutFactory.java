package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DefaultPAreaTaxonomyLayoutFactory<T extends PAreaTaxonomy> implements PAreaTaxonomyLayoutFactory<T> {

    @Override
    public BasePAreaTaxonomyLayout createLayout(PAreaTaxonomyGraph graph, T taxonomy, PAreaTaxonomyConfiguration config) {
        return new NoRegionsPAreaTaxonomyLayout(graph, taxonomy, config); 
    }
}
