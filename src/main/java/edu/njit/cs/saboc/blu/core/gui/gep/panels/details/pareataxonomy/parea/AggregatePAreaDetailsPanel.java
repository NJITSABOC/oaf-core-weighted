package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregatePAreaDetailsPanel extends NodeDetailsPanel<AggregatePArea> {
    
    public AggregatePAreaDetailsPanel(PAreaTaxonomyConfiguration config) {
        
        super(new PAreaSummaryPanel(config), 
                config.getUIConfiguration().getNodeOptionsPanel(), 
                new NodeConceptList(config),
                config);
    }
}
