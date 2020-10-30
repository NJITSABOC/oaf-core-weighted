package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffPartitionedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffAreaPanel extends DiffPartitionedNodePanel<DiffArea> {
    
    public DiffAreaPanel(DiffPAreaTaxonomyConfiguration configuration) {
        super(new DiffAreaDetailsPanel(configuration), configuration);
    }
    
    public DiffAreaPanel(DiffPAreaTaxonomyConfiguration configuration, 
            DiffAreaSummaryTextFactory textFactory) {
        
        super(new DiffAreaDetailsPanel(configuration, textFactory), configuration);
    }
}
