package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateAreaDetailsPanel extends NodeDetailsPanel<Area> {
    
    public AggregateAreaDetailsPanel(PAreaTaxonomyConfiguration configuration) {

        super(new AreaSummaryPanel(configuration), 
                configuration.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new NodeConceptList(configuration),
                configuration);
    }
}