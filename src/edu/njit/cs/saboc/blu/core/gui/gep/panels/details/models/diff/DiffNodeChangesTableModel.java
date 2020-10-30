package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class DiffNodeChangesTableModel extends OAFAbstractTableModel<AbNChange> {
    
    private final AbNTextConfiguration textConfig;
    
    public DiffNodeChangesTableModel(AbNTextConfiguration textConfig) {
        super(new String[] {
            "Change description"
        });
        
        this.textConfig = textConfig;
    }

    @Override
    protected Object[] createRow(AbNChange item) {
        
        return new Object [] {
            item.getChangeDescription(textConfig)
        };
    }
}
