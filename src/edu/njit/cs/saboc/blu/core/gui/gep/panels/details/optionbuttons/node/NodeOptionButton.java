package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.options.IconOptionButton;
import java.util.Optional;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class NodeOptionButton<T extends Node> extends IconOptionButton<T> {
    
    public NodeOptionButton(
            String iconFileName, 
            String toolTipText) {
        
        super(iconFileName, toolTipText); 
    }
    
    public void setCurrentNode(T node) {
        super.setCurrentEntity(node);
    }
    
    public Optional<T> getCurrentNode() {
        return super.getCurrentEntity();
    }
}
