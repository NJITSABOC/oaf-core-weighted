package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn.tan;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class DisjointTANConfiguration extends DisjointAbNConfiguration<DisjointNode<Cluster>> {
    
    public DisjointTANConfiguration(DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointAbN) {
        super(disjointAbN);
    }
    
    public DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> getDisjointTAN() {
        return (DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster>)super.getAbstractionNetwork();
    }
}
