package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.AbNLevelReport;

/**
 *
 * @author Chris O
 */
public class LevelReportTableModel extends OAFAbstractTableModel<AbNLevelReport> {

    public LevelReportTableModel(PartitionedAbNConfiguration config) {
        super(new String [] { 
           "Level",
            String.format("# %s", config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
    }
    
    @Override
    protected Object[] createRow(AbNLevelReport item) {
        return new Object [] {
            item.getLevel(),
            item.getContainersAtLevel().size(),
            item.getGroupsAtLevel().size(),
            item.getConceptsAtLevel().size(),
            item.getOverlappingConceptsAtLevel().size()
        };
    }
}
