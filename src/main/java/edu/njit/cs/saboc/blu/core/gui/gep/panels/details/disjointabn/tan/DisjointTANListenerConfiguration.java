package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNListenerConfiguration;

/**
 *
 * @author Chris O
 */
public abstract class DisjointTANListenerConfiguration extends DisjointAbNListenerConfiguration<DisjointNode<Cluster>> {
    
    public DisjointTANListenerConfiguration(DisjointTANConfiguration config) {
        super(config);
    }
}
