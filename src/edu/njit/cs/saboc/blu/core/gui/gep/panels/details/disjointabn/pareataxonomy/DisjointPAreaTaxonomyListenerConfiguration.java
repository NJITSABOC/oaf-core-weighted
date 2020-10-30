package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNListenerConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class DisjointPAreaTaxonomyListenerConfiguration extends DisjointAbNListenerConfiguration<DisjointNode<PArea>> {
    public DisjointPAreaTaxonomyListenerConfiguration(DisjointPAreaTaxonomyConfiguration config) {
        super(config);
    }
}
