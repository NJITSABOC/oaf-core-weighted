
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.PartitionedNodeDisjointAbN;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.graph.tan.DisjointTANFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class TANConfiguration extends PartitionedAbNConfiguration<Cluster, Band> {
    
    public TANConfiguration(ClusterTribalAbstractionNetwork tan) {
        super(tan);
    }
    
    public void setUIConfiguration(TANUIConfiguration uiConfig) {
        super.setUIConfiguration(uiConfig);
    }
    
    public void setTextConfiguration(TANTextConfiguration textConfig) {
        super.setTextConfiguration(textConfig);
    }
    
    public ClusterTribalAbstractionNetwork getAbstractionNetwork() {
        return (ClusterTribalAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    public ClusterTribalAbstractionNetwork getTribalAbstractionNetwork() {
        return this.getAbstractionNetwork();
    }

    public TANUIConfiguration getUIConfiguration() {
        return (TANUIConfiguration)super.getUIConfiguration();
    }

    public TANTextConfiguration getTextConfiguration() {
        return (TANTextConfiguration)super.getTextConfiguration();
    }

    @Override
    public int getPartitionedNodeLevel(Band node) {
        Band band = (Band)node;
        
        return band.getPatriarchs().size();
    }

    @Override
    public DisjointAbstractionNetwork<?, ?, ?> getDisjointAbstractionNetworkFor(Band node) {
        return this.getDisjointTANFor((Band)node);
    }
    
    public DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> getDisjointTANFor(Band band) {
        DisjointAbNGenerator<ClusterTribalAbstractionNetwork<Cluster>, Cluster> generator = new DisjointAbNGenerator<>();
        
        DisjointAbstractionNetwork<DisjointNode<Cluster>, ClusterTribalAbstractionNetwork<Cluster>, Cluster> disjointTAN = 
                generator.generateDisjointAbstractionNetwork(
                        new DisjointTANFactory(), 
                        getTribalAbstractionNetwork(), 
                        band.getClusters());

        return new PartitionedNodeDisjointAbN<>(disjointTAN, band);
    }
}
