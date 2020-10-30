package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.Node;

/**
 *
 * @author Chris O
 */
public class AbNConfiguration<T extends Node> {
    private final AbstractionNetwork<T> abstractionNetwork;
    private AbNUIConfiguration uiConfiguration;
    private AbNTextConfiguration textConfiguration;
    
    public AbNConfiguration(AbstractionNetwork<T> abstractionNetwork) {
        this.abstractionNetwork = abstractionNetwork;
    }
    
    public void setUIConfiguration(AbNUIConfiguration<T> uiConfiguration) {
        this.uiConfiguration = uiConfiguration;
    }

    public void setTextConfiguration(AbNTextConfiguration<T> textConfiguration) {
        this.textConfiguration = textConfiguration; 
    }
    
    public AbstractionNetwork<T> getAbstractionNetwork() {
        return abstractionNetwork;
    }
    
    public AbNUIConfiguration<T> getUIConfiguration() {
        return uiConfiguration;
    }
    
    public AbNTextConfiguration<T> getTextConfiguration() {
        return textConfiguration;
    }
}
