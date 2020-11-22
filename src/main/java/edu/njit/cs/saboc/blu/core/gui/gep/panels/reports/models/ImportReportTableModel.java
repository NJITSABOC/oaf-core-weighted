package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.models;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.entry.ImportedConceptNodeReport;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Chris O
 */
public class ImportReportTableModel extends OAFAbstractTableModel<ImportedConceptNodeReport> {

    private final AbNConfiguration config;

    public ImportReportTableModel(AbNConfiguration config) {
        super(new String [] {
            String.format("%s ID", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("%s Name", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false)),
            String.format("# %s", config.getTextConfiguration().getNodeTypeName(true)), 
            config.getTextConfiguration().getNodeTypeName(true)
        });
        
        this.config = config;
    }
    
    @Override
    protected Object[] createRow(ImportedConceptNodeReport item) {
        ArrayList<String> groupNames = new ArrayList<>();
        
        item.getNodes().forEach( (group) -> {
            groupNames.add(String.format("%s (%d)", group.getName(), group.getConceptCount()));
        });
        
        Collections.sort(groupNames);
        
        String groupsStr;
        
        if (!groupNames.isEmpty()) {
            groupsStr = groupNames.get(0);

            for (int c = 1; c < groupNames.size(); c++) {
                groupsStr += "\n" + groupNames.get(c);
            }
        } else {
            groupsStr = "(none)";
        }
        
        return new Object[] {
            item.getConcept().getName(),
            item.getConcept().getIDAsString(),
            item.getNodes().size(),
            groupsStr
        };
    }
}
