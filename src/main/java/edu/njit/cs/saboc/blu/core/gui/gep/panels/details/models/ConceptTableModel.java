
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ConceptTableModel extends OAFAbstractTableModel<Concept> {
    
    public ConceptTableModel(AbNConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false),
        });
    }
    
    public Concept getConceptAtRow(int row) {
        return super.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(Concept concept) {
        return new Object [] {concept};
    }
}
