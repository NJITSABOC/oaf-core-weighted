package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CreateTANFromPartitionedNodeButton<T extends PartitionedNode> extends CreateTANButton<T> {

    private final TANFactory factory;
    
    private final PartitionedAbNConfiguration config;
    
    public CreateTANFromPartitionedNodeButton(
            TANFactory factory,
            PartitionedAbNConfiguration config,
            DisplayAbNAction listener) {
        
        super(config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(false).toLowerCase(), 
                listener);
        
        this.factory = factory;
        
        this.config = config;
    }

    @Override
    public ClusterTribalAbstractionNetwork deriveTAN() {
        T partitionedNode = super.getCurrentNode().get();

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        
        ClusterTribalAbstractionNetwork tan = generator.createTANFromPartitionedNode(
                config.getAbstractionNetwork(), 
                partitionedNode, 
                factory);
        
        return tan;
    }

    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(node.hasOverlappingConcepts());
    }
}
