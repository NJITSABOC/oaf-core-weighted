package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface PAreaTaxonomyLayoutFactory<T extends PAreaTaxonomy> {
    
    public BasePAreaTaxonomyLayout createLayout(
            PAreaTaxonomyGraph graph, 
            T taxonomy, 
            PAreaTaxonomyConfiguration config);
}
