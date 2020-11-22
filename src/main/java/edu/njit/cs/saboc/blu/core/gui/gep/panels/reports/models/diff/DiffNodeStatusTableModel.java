package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff.DiffNodeStatusReport;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.diff.DiffNodeStatusReport.DiffNodeStatus;

/**
 *
 * @author Chris O
 */
public class DiffNodeStatusTableModel extends OAFAbstractTableModel<DiffNodeStatusReport> {
    
    private final AbNConfiguration config;
    
    public DiffNodeStatusTableModel(AbNConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getNodeTypeName(false),
            "Status as Root",
            "Concept State"
        });
        
        this.config = config;
    }

    @Override
    protected Object[] createRow(DiffNodeStatusReport item) {
        return new Object[] {
            item.getNode().getName(),
            getRootState(item.getStatus()),
            getRootStateType(item.getStatus())
        };
    }
    
    private String getRootState(DiffNodeStatus status) {
        switch (status) {
            case MovedExactly:
            case MovedSubset:
            case MovedSuperset:
            case MovedDifference:
                
                return "Moved";
            case IntroducedFromNew:
            case IntroducedFromOneNode:
            case IntroducedFromMultipleNodes:
                
                return "Introduced";
            case RemovedFromOnt:
            case RemovedToOneNode:
            case RemovedToMultipleNodes:
                
                return "Removed";
            default:
                
                return "[UNKNOWN STATUS]";
        }
    }
    
    private String getRootStateType(DiffNodeStatus status) {
        switch (status) {
            case MovedExactly:
                return "Exact";
                
            case MovedSubset:
                return "Subset";
                
            case MovedSuperset:
                return "Superset";
                
            case MovedDifference:
                return "Different";
                
            case IntroducedFromNew:
                return String.format("All new %s", 
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase());
                
            case IntroducedFromOneNode:
                return String.format("From one %s", 
                        config.getTextConfiguration().getNodeTypeName(false).toLowerCase());
                
            case IntroducedFromMultipleNodes:
                return String.format("From multiple %s", 
                        config.getTextConfiguration().getNodeTypeName(true).toLowerCase());
                
            case RemovedFromOnt:
                    return String.format("All %s removed", 
                        config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true).toLowerCase());
                
            case RemovedToOneNode:
                    return String.format("To one %s", 
                        config.getTextConfiguration().getNodeTypeName(false).toLowerCase());
                
            case RemovedToMultipleNodes:
                    return String.format("To multiple %s", 
                        config.getTextConfiguration().getNodeTypeName(true).toLowerCase());
                
            default:
                return "[UNKNOWN STATUS TYPE]";
        }
    }
    
}
