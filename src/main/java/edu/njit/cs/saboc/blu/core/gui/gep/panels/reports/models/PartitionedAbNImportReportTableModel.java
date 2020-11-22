package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ImportedConceptNodeReport;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class PartitionedAbNImportReportTableModel extends OAFAbstractTableModel<ImportedConceptNodeReport> {

    private final PartitionedAbNConfiguration config;

    public PartitionedAbNImportReportTableModel(PartitionedAbNConfiguration config) {
        super(new String [] {
            String.format("%s ID", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("%s Name", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getNodeTypeName(true)), 
            config.getTextConfiguration().getNodeTypeName(true), 
            "Level",
            config.getTextConfiguration().getBaseAbNTextConfiguration().getNodeTypeName(false)
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ImportedConceptNodeReport item) {
        ArrayList<String> groupNames = new ArrayList<>();
        
        SinglyRootedNode theNode = (SinglyRootedNode)item.getNodes().iterator().next();
        
        PartitionedNode pNode = config.getAbstractionNetwork().getPartitionNodeFor(theNode);
        
        item.getNodes().forEach( (group) -> {
            groupNames.add(group.getName());
        });
        
        Collections.sort(groupNames);
        
        String groupsStr;
        
        if (!groupNames.isEmpty()) {
            groupsStr = groupNames.get(0);

            for (int c = 1; c < groupNames.size(); c++) {
                groupsStr += ", " + groupNames.get(c);
            }
        } else {
            groupsStr = "(none)";
        }
        
        return new Object[] {
            item.getConcept().getName(),
            item.getConcept().getIDAsString(),
            item.getNodes().size(),
            groupsStr,
            config.getPartitionedNodeLevel(pNode), 
            pNode.getName()
        };
    }
}
