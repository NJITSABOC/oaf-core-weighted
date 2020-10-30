package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 * @param <T>
 * @param <V>
 */
public abstract class PartitionedAbNTextConfiguration<T extends SinglyRootedNode, V extends PartitionedNode> extends AbNTextConfiguration<T> {
    
    public PartitionedAbNTextConfiguration(OntologyEntityNameConfiguration ontologyEntityNameConfiguration) {
        super(ontologyEntityNameConfiguration);
    }
     
    public abstract AbNTextConfiguration getBaseAbNTextConfiguration();
        
    public abstract String getDisjointNodeTypeName(boolean plural);
}
