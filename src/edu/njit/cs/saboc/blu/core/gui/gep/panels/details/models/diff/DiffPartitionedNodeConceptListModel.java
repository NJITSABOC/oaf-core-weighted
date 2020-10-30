
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPartitionedNodeConceptListModel extends DiffNodeConceptListModel {
    
    public DiffPartitionedNodeConceptListModel(PartitionedAbNConfiguration config) {
        super(config, config.getTextConfiguration().getBaseAbNTextConfiguration());
    }
    
    protected PartitionedAbNConfiguration getConfiguration() {
        return (PartitionedAbNConfiguration)super.getConfiguration();
    }    
}
