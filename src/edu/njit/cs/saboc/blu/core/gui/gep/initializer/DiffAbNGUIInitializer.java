package edu.njit.cs.saboc.blu.core.gui.gep.initializer;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.DiffTaxonomySubsetSelectionButton.DiffTaxonomySubsetCreationAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.DiffTaxonomySubsetSelectionPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;

/**
 *
 * @author Chris O
 */
public class DiffAbNGUIInitializer extends BaseAbNExplorationPanelInitializer {
    
    private final DiffTaxonomySubsetCreationAction creationAction;
    
    public DiffAbNGUIInitializer(
            AbNWarningManager warningManager, 
            DiffTaxonomySubsetCreationAction creationAction) {
        
        super(warningManager);
        
        this.creationAction = creationAction;
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        
        super.initializeAbNDisplayPanel(displayPanel, startUp);
        
        DiffTaxonomySubsetSelectionPanel subsetPanel = 
                new DiffTaxonomySubsetSelectionPanel(displayPanel, creationAction);
        
        displayPanel.addWidget(subsetPanel);
    }
}