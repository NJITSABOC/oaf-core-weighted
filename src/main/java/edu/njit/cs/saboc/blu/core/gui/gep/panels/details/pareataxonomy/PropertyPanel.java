package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class PropertyPanel extends AbstractEntityList<InheritableProperty>  {
    private final AbNConfiguration config;
    
    public PropertyPanel(AbNConfiguration config, OAFAbstractTableModel<InheritableProperty> tableModel) {
        super(tableModel);
        
        this.config = config;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<InheritableProperty>> entities) {
        
        if(entities.isPresent()) {
            return String.format("%s (%d)", 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getPropertyTypeName(true),
                    entities.get().size());
        } else {
            return String.format("%s (0)", config.getTextConfiguration().getOntologyEntityNameConfiguration().getPropertyTypeName(true));
        }
    }
}
