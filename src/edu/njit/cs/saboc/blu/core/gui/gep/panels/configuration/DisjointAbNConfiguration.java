package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;

/**
 *
 * @author Chris O
 */
public class DisjointAbNConfiguration<T extends DisjointNode> extends AbNConfiguration<T> {

    public DisjointAbNConfiguration(DisjointAbstractionNetwork disjointAbN) {
        super(disjointAbN);
    }
    
    public void setUIConfiguration(DisjointAbNUIConfiguration<T> config) {
        super.setUIConfiguration(config);
    }
    
    public void setTextConfiguration(DisjointAbNTextConfiguration<T> config) {
        super.setTextConfiguration(config);
    }
    
    public DisjointAbstractionNetwork getAbstractionNetwork() {
        return (DisjointAbstractionNetwork)super.getAbstractionNetwork();
    }
    
    public DisjointAbNUIConfiguration<T> getUIConfiguration() {
        return (DisjointAbNUIConfiguration<T>) super.getUIConfiguration();
    }

    @Override
    public DisjointAbNTextConfiguration<T> getTextConfiguration() {
        return (DisjointAbNTextConfiguration<T>) super.getTextConfiguration();
    }
}
