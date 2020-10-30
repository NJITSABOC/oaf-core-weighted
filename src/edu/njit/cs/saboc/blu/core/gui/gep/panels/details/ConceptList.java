package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.ConceptTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.utils.renderers.ConceptListConceptRenderer;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class ConceptList extends AbstractEntityList<Concept> {
    
    private final AbNConfiguration config;
    
    public ConceptList(AbNConfiguration config) {
        this(config, new ConceptTableModel(config));
    }
    
    public ConceptList(AbNConfiguration config, OAFAbstractTableModel<Concept> model) {
        super(model);
        
        this.config = config;
        
        this.setDefaultTableRenderer(Concept.class, new ConceptListConceptRenderer<>());
    }

    @Override
    protected String getBorderText(Optional<ArrayList<Concept>> entities) {
        String base = config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true);

        if(entities.isPresent()) {
            return String.format("%s (%d)", base, entities.get().size());
        } else {
            return String.format("%s (0)", base);
        }
    }
}
