package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ContainerReport;

/**
 *
 * @author Chris O
 */
public class ContainerReportTableModel extends OAFAbstractTableModel<ContainerReport> {
    
    private final PartitionedAbNConfiguration config;
    
    public ContainerReportTableModel(PartitionedAbNConfiguration config) {
        
        super(new String[] {
            config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(false),
            "Level",
            String.format("# %s", config.getTextConfiguration().getNodeTypeName(true)),
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            String.format("# Overlapping %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ContainerReport item) {
        return new Object[] {
            item.getContainer().getName(",\n"),
            config.getPartitionedNodeLevel(item.getContainer()),
            item.getGroups().size(),
            item.getConcepts().size(),
            item.getOverlappingConcepts().size()
        };
    }
}
