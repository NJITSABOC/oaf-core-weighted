package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import edu.njit.cs.saboc.blu.core.abn.node.OverlappingConceptDetails;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class OverlappingConceptReportTableModel extends OAFAbstractTableModel<OverlappingConceptDetails<?>>  {
    
    private final PartitionedAbNConfiguration config;
    
    public OverlappingConceptReportTableModel(PartitionedAbNConfiguration config) {
        super(new String [] {
            String.format("Overlapping %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            "Degree of Overlap",
            String.format("Overlapping %s", config.getTextConfiguration().getNodeTypeName(true)), 
            String.format("%s", config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(false))
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(OverlappingConceptDetails<?> item) {
        
        String overlappingConceptName = item.getConcept().getName();
        
        ArrayList<String> overlappingGroupNames = new ArrayList<>();
        
        item.getNodes().forEach((group) -> {
            overlappingGroupNames.add(String.format("%s (%d)", group.getName(), group.getConceptCount()));
        });
        
        Collections.sort(overlappingGroupNames);
        
        String overlappingGroups = overlappingGroupNames.get(0);
        
        for(int c = 1; c < overlappingGroupNames.size(); c++) {
            overlappingGroups += "\n" + overlappingGroupNames.get(c);
        }
        
        String areaName = config.getAbstractionNetwork().getPartitionNodeFor(item.getNodes().iterator().next()).getName();
        
        return new Object[] {
            overlappingConceptName,
            item.getNodes().size(),
            overlappingGroups,
            areaName
        };
    }
}
