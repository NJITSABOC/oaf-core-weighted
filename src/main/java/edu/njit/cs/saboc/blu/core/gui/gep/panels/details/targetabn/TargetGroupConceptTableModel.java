package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.IncomingRelationshipDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.AbstractNodeEntityTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class TargetGroupConceptTableModel extends AbstractNodeEntityTableModel<Concept, TargetGroup> {
    
    public TargetGroupConceptTableModel(TargetAbNConfiguration config) {
        
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false),
            String.format("# Source %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
    }

    @Override
    protected Object[] createRow(Concept concept) {
        
        int sourceCount = -1;
        
        if(super.getCurrentNode().isPresent()) {
            TargetGroup group = super.getCurrentNode().get();
            
            IncomingRelationshipDetails details = group.getIncomingRelationshipDetails();
            
            sourceCount = details.getIncomingRelationshipsTo(concept).size();
        }
        
        return new Object [] {
            concept,
            sourceCount
        };
    }
}
