package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.DisplayNewTAN;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;
import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class CreateTANButton<T extends Node> extends NodeOptionButton<T> {
    
    private final DisplayAbNAction<ClusterTribalAbstractionNetwork> displayTAN;
    
    public CreateTANButton(String forNodeType, DisplayAbNAction<ClusterTribalAbstractionNetwork> displayTAN) {
        
        super("BluTAN.png", String.format("Derive Tribal Abstraction Network (TAN) from %s", forNodeType));
        
        this.addActionListener((ActionEvent ae) -> {
            createAndDisplayTAN();
        });
        
        this.displayTAN = displayTAN;
    }
    
    public void createAndDisplayTAN() {
        
        if (getCurrentNode().isPresent()) {
            DisplayNewTAN display = new DisplayNewTAN(
                    this, 
                    this.displayTAN, 
                    "Creating Tribal Abstraction Network (TAN)");
            
            display.startThread();
        }
        
    }
    
    public abstract ClusterTribalAbstractionNetwork deriveTAN();
}

