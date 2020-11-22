package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ParentTargetGroupTableModel extends OAFAbstractTableModel<ParentNodeDetails<TargetGroup>> {
    
    private final TargetAbNConfiguration config;
    
    public ParentTargetGroupTableModel(TargetAbNConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false),
            "Parent Target Group",
            String.format("# Target %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            String.format("# Source %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    public Concept getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    @Override
    protected Object[] createRow(ParentNodeDetails<TargetGroup> parentInfo) {
     
        TargetGroup parentGroup = parentInfo.getParentNode();
        
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentGroup.getName(),
            parentGroup.getIncomingRelationshipTargets().size(),
            parentGroup.getIncomingRelationshipSources().size()
        };
    }
}