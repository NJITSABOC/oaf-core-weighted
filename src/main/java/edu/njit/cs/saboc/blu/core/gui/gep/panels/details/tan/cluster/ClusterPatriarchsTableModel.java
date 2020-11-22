package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.cluster;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ClusterPatriarchsTableModel extends OAFAbstractTableModel<Concept> {

    private final TANConfiguration config;
    
    public ClusterPatriarchsTableModel(TANConfiguration config) {
        super(new String[]{"Tribe Patriarch Name"});
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(Concept patriarch) {
        return new Object[] {
            patriarch.getName()
        };
    }
}
