package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeTableModel<T extends Node> extends OAFAbstractTableModel<OverlappingNodeEntry<T>> {
    
    private final AbNConfiguration configuration;
    
    public OverlappingNodeTableModel(AbNConfiguration configuration) {
        
        super(new String [] {
            configuration.getTextConfiguration().getNodeTypeName(false),
            String.format("# %s", configuration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", configuration.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
        });
        
        this.configuration = configuration;
    }

    @Override
    protected Object[] createRow(OverlappingNodeEntry<T> item) {
        return new Object [] {
            item.getOverlappingNode().getName(),
            item.getOverlappingNode().getConceptCount(),
            item.getOverlappingConcepts().size()
        };
    }    
}
