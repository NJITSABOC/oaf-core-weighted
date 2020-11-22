package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class CreateTANFromSinglyRootedNodeButton<T extends SinglyRootedNode> extends CreateTANButton<T> {
    
    private final TANFactory factory;
    
    private final AbNConfiguration config;
    
    public CreateTANFromSinglyRootedNodeButton(
            TANFactory factory,
            AbNConfiguration config, 
            DisplayAbNAction displayTAN) {
        
        super(config.getTextConfiguration().getNodeTypeName(false).toLowerCase(), displayTAN);
        
        this.factory = factory;
        this.config = config;
    }

    @Override
    public ClusterTribalAbstractionNetwork deriveTAN() {
        SinglyRootedNode currentNode = (SinglyRootedNode)super.getCurrentNode().get();
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        
        ClusterTribalAbstractionNetwork tan = generator.createTANFromSinglyRootedNode(
                config.getAbstractionNetwork(), currentNode, factory);
        
        return tan;
    }

    @Override
    public void setEnabledFor(T node) {
        if(node.getHierarchy().getChildren(node.getHierarchy().getRoot()).size() > 1) {
            this.setEnabled(true);
        } else {
            this.setEnabled(false);
        }
    }
}
