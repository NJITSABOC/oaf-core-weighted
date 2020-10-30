package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class BandPatriarchTableModel extends OAFAbstractTableModel<Concept> {

    private final TANConfiguration config;
    
    public BandPatriarchTableModel(TANConfiguration config) {
        super(new String[]{"Tribal Patriarch"});
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(Concept patriarch) {
        return new Object[] {
            patriarch.getName()
        };
    }
}
