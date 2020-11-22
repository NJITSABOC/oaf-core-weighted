package edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *  Factory for creating listeners for various UI elements in the GEP
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class AbNListenerConfiguration<T extends Node> {
    
    private final AbNConfiguration config;
    
    public AbNListenerConfiguration(AbNConfiguration config) {
        this.config = config;
    }
    
    /**
     * Returns the parent configuration for the current GEP
     * @return 
     */
    protected final AbNConfiguration getConfiguration() {
        return config;
    }
    
    /**
     * Creates a listener for when concepts are selected from a node's concept list
     * 
     * @return 
     */
    public EntitySelectionListener<Concept> getGroupConceptListListener() {
        return new EntitySelectionAdapter<>();
    }
    
    public abstract EntitySelectionListener<T> getChildGroupListener();
    
    public abstract EntitySelectionListener<ParentNodeDetails<T>> getParentGroupListener();
}
