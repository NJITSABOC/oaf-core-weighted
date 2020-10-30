package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class AggregateBandDetailsPanel extends NodeDetailsPanel<Band> {
    
    public AggregateBandDetailsPanel(TANConfiguration configuration) {

        super(new BandSummaryPanel(configuration), 
                configuration.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new NodeConceptList(configuration),
                configuration);
    }
}