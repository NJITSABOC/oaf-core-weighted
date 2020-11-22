package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class PAreaSummaryTextFactory extends NodeSummaryTextFactory<PArea> {

    public PAreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public PAreaTaxonomyConfiguration getConfiguration() {
        return (PAreaTaxonomyConfiguration)super.getConfiguration();
    }
}
