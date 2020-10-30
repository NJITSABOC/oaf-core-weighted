package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.DisjointAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class OverlappingNodeTableModel<T extends Node> extends OAFAbstractTableModel<T>  {
    
    private final DisjointAbNConfiguration config;
    
    public OverlappingNodeTableModel(DisjointAbNConfiguration config) {
        super(new String[] { 
            String.format("Overlapping %s", config.getTextConfiguration().getOverlappingNodeTypeName(true)) 
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(T node) {
        return new Object[] {
            node.getName()
        };
    }
}
