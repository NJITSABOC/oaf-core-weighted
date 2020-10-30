package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NodeConceptListTableModel<T extends Node> extends AbstractNodeEntityTableModel<Concept, T> {
    
    private final AbNConfiguration config;
    
    public NodeConceptListTableModel(AbNConfiguration config) {
        super(new String[] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)
        });
        
        this.config = config;
    }
    
    protected AbNConfiguration getConfiguration() {
        return config;
    }

    @Override
    protected Object[] createRow(Concept item) {
        return new Object [] { item };
    }
}
