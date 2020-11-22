package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNUIConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.AbNDisplayManager;

/**
 *
 * @author Chris O
 */
public abstract class DisjointTANUIConfiguration extends DisjointAbNUIConfiguration<DisjointNode<Cluster>> {
    
    public DisjointTANUIConfiguration(
            DisjointTANConfiguration config, 
            AbNDisplayManager displayManager,
            DisjointTANListenerConfiguration listenerConfig) {
        
        super(config, displayManager, listenerConfig);
    }
}
