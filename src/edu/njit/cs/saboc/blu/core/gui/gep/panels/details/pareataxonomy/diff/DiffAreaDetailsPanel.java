package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffNodeConceptRightClickMenuGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeConceptListModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffPartitionedNodeConceptListModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AreaSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffAreaDetailsPanel extends NodeDetailsPanel<DiffArea> {
    
    public DiffAreaDetailsPanel(DiffPAreaTaxonomyConfiguration config) {
        this(config, new DiffAreaSummaryTextFactory(config));
    }
    
    public DiffAreaDetailsPanel(DiffPAreaTaxonomyConfiguration config, 
            DiffAreaSummaryTextFactory textFactory) {
        
        super(new AreaSummaryPanel(config, textFactory),
                config.getUIConfiguration().getPartitionedNodeOptionsPanel(),
                new NodeConceptList(new DiffPartitionedNodeConceptListModel(config), config),
                config);
        
        DiffNodeConceptListModel model = (DiffNodeConceptListModel)super.getConceptList().getTableModel();
        
        this.getConceptList().setRightClickMenuGenerator(new DiffNodeConceptRightClickMenuGenerator(
                    config, 
                    config.getTextConfiguration().getBaseAbNTextConfiguration(),
                    model));
    }
}
