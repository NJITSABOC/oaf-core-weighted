package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;

/**
 *
 * @author Chris O
 */
public class DiffNodeRootChangeExplanationModel extends OAFAbstractTableModel<DiffAbNConceptChange> {
    
    public interface ChangeExplanationRowEntryFactory {
        public ChangeExplanationRowEntry getChangeEntry(DiffAbNConceptChange item);
    }
    
    private final ChangeExplanationRowEntryFactory factory;
    
    public DiffNodeRootChangeExplanationModel(ChangeExplanationRowEntryFactory factory) {
        super(new String [] {
           "Type", 
            "Inheritance", 
            "Summary"
        });
        
        this.factory = factory;
    }

    @Override
    protected Object[] createRow(DiffAbNConceptChange item) {
        return factory.getChangeEntry(item).asRow();
    }
}
