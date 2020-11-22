package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffNodeInstance;
import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DiffNodeTableModel<T extends Node> extends OAFAbstractTableModel<T> {
    
    public DiffNodeTableModel(AbNConfiguration configuration) {
        super(new String[] {
                    configuration.getTextConfiguration().getNodeTypeName(false),
            
                    String.format("# %s", 
                            configuration.getTextConfiguration().getOntologyEntityNameConfiguration()
                                    .getConceptTypeName(true)),
                    
                    "Diff State"
            });
    }

    @Override
    protected Object[] createRow(T node) {
        
        ChangeState state = ((DiffNodeInstance)node).getDiffNode().getChangeDetails().getNodeState();
        
        return new Object [] {
            node.getName(),
            node.getConceptCount(),
            state
        };
    }
}