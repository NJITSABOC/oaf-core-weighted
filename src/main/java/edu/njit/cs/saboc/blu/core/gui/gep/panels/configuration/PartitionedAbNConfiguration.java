package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 */
public abstract class PartitionedAbNConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNConfiguration<T> {
    
    public PartitionedAbNConfiguration(PartitionedAbstractionNetwork<T, V> abstractionNetwork) {
        super(abstractionNetwork);
    }
    
    public abstract int getPartitionedNodeLevel(V node);
    
    public abstract DisjointAbstractionNetwork<?, ?, ?> getDisjointAbstractionNetworkFor(V node);
    
    public void setUIConfiguration(PartitionedAbNUIConfiguration<T, V> config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(PartitionedAbNTextConfiguration<T, V> config) {
        super.setTextConfiguration(config);
    }

    @Override
    public PartitionedAbstractionNetwork getAbstractionNetwork() {
        return (PartitionedAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    @Override
    public PartitionedAbNUIConfiguration<T, V> getUIConfiguration() {
        return (PartitionedAbNUIConfiguration<T, V>)super.getUIConfiguration();
    }
    
    @Override
    public PartitionedAbNTextConfiguration<T, V> getTextConfiguration() {
        return (PartitionedAbNTextConfiguration<T, V>)super.getTextConfiguration();
    }
}
