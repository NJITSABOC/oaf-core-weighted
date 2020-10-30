package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;

/**
 *
 * @author Chris O
 */
public class ClusterSummaryTextFactory extends NodeSummaryTextFactory<Cluster> {
        
    public ClusterSummaryTextFactory(TANConfiguration config) {
        super(config);
    }
    
    @Override
    public TANConfiguration getConfiguration() {
        return (TANConfiguration)super.getConfiguration();
    }
}
