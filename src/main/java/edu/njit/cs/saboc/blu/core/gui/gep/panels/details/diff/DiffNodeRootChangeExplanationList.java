
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import edu.njit.cs.saboc.blu.core.gui.utils.renderers.SingleLineTextRenderer;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class DiffNodeRootChangeExplanationList extends AbstractEntityList<DiffAbNConceptChange> {

    private final AbNTextConfiguration textConfig;
    
    public DiffNodeRootChangeExplanationList(
            ChangeExplanationRowEntryFactory entryFactory, 
            AbNTextConfiguration textConfig) {
        
        super(new DiffNodeRootChangeExplanationModel(entryFactory));
        
        this.setDefaultTableRenderer(String.class, new SingleLineTextRenderer(this.getEntityTable()));
        
        this.textConfig = textConfig;
    }

    @Override
    protected String getBorderText(Optional<ArrayList<DiffAbNConceptChange>> entities) {
        
        String base = String.format("Structural Changes that Affected Root %s", 
                textConfig.getOntologyEntityNameConfiguration().getConceptTypeName(false));
        
        int count = 0;
        
        if(entities.isPresent()) {
            count = entities.get().size();
        }
        
        return String.format("%s (%d)", base, count);
    }
}
