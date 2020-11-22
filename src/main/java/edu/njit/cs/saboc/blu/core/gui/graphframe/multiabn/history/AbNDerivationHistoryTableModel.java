package edu.njit.cs.saboc.blu.core.gui.graphframe.multiabn.history;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.text.SimpleDateFormat;

/**
 *
 * @author Chris O
 */
public class AbNDerivationHistoryTableModel extends OAFAbstractTableModel<AbNDerivationHistoryEntry> {
    
    public AbNDerivationHistoryTableModel() {
        super(new String [] {
            "Date/Time Created",
            "Abstraction Network Name"
        });
    }

    @Override
    protected Object[] createRow(AbNDerivationHistoryEntry item) {
        SimpleDateFormat entryTimeFormat = new SimpleDateFormat ("MM/dd/yyyy hh:mm:ss a");
        
        return new Object[]{
            entryTimeFormat.format(item.getDate()),
            item.getDerivation().getName()
        };
    }
    
}
