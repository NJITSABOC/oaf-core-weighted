package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.AbNChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeChangesTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class DiffNodeChangeList extends AbstractEntityList<AbNChange> {
    
    private final AbNConfiguration configuration;
    
    public DiffNodeChangeList(
            AbNConfiguration configuration,
            AbNTextConfiguration textConfig) {
        
        this(new DiffNodeChangesTableModel(textConfig), configuration);
    }
    
    public DiffNodeChangeList(OAFAbstractTableModel<AbNChange> model, AbNConfiguration configuration) {
        super(model);
        
        this.configuration = configuration;
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<AbNChange>> entities) {
        if(entities.isPresent()) {
            return String.format("Changes (%d)", entities.get().size());
        } else {
            return "Changes";
        }
    }
}
