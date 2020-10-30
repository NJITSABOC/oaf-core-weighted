package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeConceptList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffNodeConceptRightClickMenuGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeConceptListModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaSummaryPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPAreaDetailsPanel extends NodeDetailsPanel<DiffPArea> {
    
    public DiffPAreaDetailsPanel(DiffPAreaTaxonomyConfiguration config) {
        this(config, new DiffPAreaSummaryTextFactory(config));
    }
    
    public DiffPAreaDetailsPanel(
            DiffPAreaTaxonomyConfiguration config, 
            DiffPAreaSummaryTextFactory textFactory) {
        
        super(new PAreaSummaryPanel(config, textFactory),
                config.getUIConfiguration().getNodeOptionsPanel(),
                new NodeConceptList(new DiffNodeConceptListModel(config, config.getTextConfiguration()), config),
                config);
        
        DiffNodeConceptListModel model = (DiffNodeConceptListModel)super.getConceptList().getTableModel();
        
        this.getConceptList().setRightClickMenuGenerator( new DiffNodeConceptRightClickMenuGenerator(
                    config, 
                    config.getTextConfiguration(),
                    model));
    }
}