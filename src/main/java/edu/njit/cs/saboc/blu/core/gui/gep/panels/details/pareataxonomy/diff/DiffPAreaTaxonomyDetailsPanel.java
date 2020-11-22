package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff.DiffNodeStatusReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff.DiffPAreaImplicitChangePanel;

/**
 *
 * @author Chris O
 */
public class DiffPAreaTaxonomyDetailsPanel extends AbNDetailsPanel<DiffPAreaTaxonomy> {
    
    private final DiffPAreaImplicitChangePanel implicitChangesPanel;
    private final DiffNodeStatusReportPanel nodeStatusReportPanel;
    
    
    public DiffPAreaTaxonomyDetailsPanel(DiffPAreaTaxonomyConfiguration config) {
        super(config);
        
        this.implicitChangesPanel = new DiffPAreaImplicitChangePanel(config);
        
        super.addDetailsTab("Implicitly Changed Diff Partial-areas", implicitChangesPanel);
        implicitChangesPanel.displayAbNReport(config.getPAreaTaxonomy());
        

        this.nodeStatusReportPanel = new DiffNodeStatusReportPanel(config);
        
        super.addDetailsTab("Diff Parital-area Status", nodeStatusReportPanel);
        nodeStatusReportPanel.displayAbNReport(config.getAbstractionNetwork());
    }
}