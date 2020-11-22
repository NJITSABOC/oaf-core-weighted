package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;

/**
 *
 * @author Chris O
 */
public class ChildNodeTableModel<T extends Node> extends OAFAbstractTableModel<T> {

    public ChildNodeTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public ChildNodeTableModel(AbNConfiguration config) {
        super(new String [] {
            String.format("Child %s", config.getTextConfiguration().getNodeTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
    }
    
    public Node getChildNode(int row) {
        return this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(T item) {
        return new Object [] {
            item.getName(),
            item.getConceptCount()
        };
    }
}
