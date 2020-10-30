package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeHierarchyPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.diff.DiffSinglyRootedNodePanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.diff.DiffNodeRootChangeExplanationModel.ChangeExplanationRowEntryFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPAreaPanel extends DiffSinglyRootedNodePanel<DiffPArea> {
    
    private final DiffPAreaTaxonomyConfiguration diffConfiguration;
    
    public DiffPAreaPanel(
            DiffPAreaTaxonomyConfiguration configuration, 
            ChangeExplanationRowEntryFactory changeExplanationFactory) {
        
        super(new DiffPAreaDetailsPanel(configuration),
                
                new NodeHierarchyPanel(
                        configuration,
                        new DiffParentPAreaTableModel(configuration),
                        new DiffChildPAreaTableModel(configuration)), 
                changeExplanationFactory, 
                configuration);
        
        this.diffConfiguration = configuration;
    }
    
    public DiffPAreaPanel(
            DiffPAreaTaxonomyConfiguration configuration,  
            DiffPAreaSummaryTextFactory textFactory, 
            ChangeExplanationRowEntryFactory changeExplanationFactory) {
        
        super(new DiffPAreaDetailsPanel(configuration, textFactory),
                new NodeHierarchyPanel(
                        configuration,
                        new DiffParentPAreaTableModel(configuration),
                        new DiffChildPAreaTableModel(configuration)),
                
                changeExplanationFactory,
                configuration);
        
        this.diffConfiguration = configuration;
    }
}
