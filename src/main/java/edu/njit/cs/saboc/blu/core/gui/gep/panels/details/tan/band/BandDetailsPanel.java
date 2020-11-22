package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.PartitionNodeOverlappingConceptTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class BandDetailsPanel extends NodeDetailsPanel<Band> {
    
    public BandDetailsPanel(TANConfiguration config) {
        super(new BandSummaryPanel(config), 
                config.getUIConfiguration().getPartitionedNodeOptionsPanel(), 
                new NodeConceptList(new PartitionNodeOverlappingConceptTableModel(config), config),
                config);
        
        super.getConceptList().getEntityTable().getColumnModel().getColumn(1).setMaxWidth(80);
    }
    
}
