package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.PartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class BandPanel extends PartitionedNodePanel<Band> {
    
    private final TANConfiguration config;

    public BandPanel(TANConfiguration config) {
        super(new BandDetailsPanel(config), 
                config);
        
        this.config = config;
    }
}
