
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;

/**
 *
 * @author Chris O
 */
public class ChildTargetGroupTableModel extends OAFAbstractTableModel<TargetGroup> {

    private final TargetAbNConfiguration config;
    
    public ChildTargetGroupTableModel(TargetAbNConfiguration config) {
        super(new String [] {
            "Child Target Group",
            String.format("# Target %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            String.format("# Source %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }

    @Override
    protected Object[] createRow(TargetGroup childGroup) {
        
        return new Object [] {
            childGroup.getName(),
            childGroup.getIncomingRelationshipTargets().size(),
            childGroup.getIncomingRelationshipSources().size()
        };
    }
}
