package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class DiffChildNodeTableModel<T extends Node> extends OAFAbstractTableModel<T> {

    public DiffChildNodeTableModel(String [] columnNames) {
        super(columnNames);
    }
    
    public DiffChildNodeTableModel(AbNConfiguration config) {
        this(new String [] {
            String.format("Child %s", config.getTextConfiguration().getNodeTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Diff State"
        });
    }
    
    public Node getChildNode(int row) {
        return this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(Node item) {
        
        ChangeState state = ((DiffNodeInstance)item).getDiffNode().getChangeDetails().getNodeState();
        
        return new Object [] {
            item.getName(),
            item.getConceptCount(),
            state
        };
    }
}