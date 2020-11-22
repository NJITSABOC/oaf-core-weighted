package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Chris O
 */
public class SourceConceptList extends NodeConceptList<TargetGroup> {
    
    private final TargetAbNConfiguration config;
    
    public SourceConceptList(TargetAbNConfiguration config) {
        
        super(new SourceConceptTableModel(config), config);
        
        this.config = config;
    }
    
    @Override
    protected String getBorderText(Optional<ArrayList<Concept>> entities) {
        if(entities.isPresent()) {
            return String.format("Source %s (%d)", 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true),
                    entities.get().size());
            
        } else {
            return "Source Concepts";
        }
    }
}
