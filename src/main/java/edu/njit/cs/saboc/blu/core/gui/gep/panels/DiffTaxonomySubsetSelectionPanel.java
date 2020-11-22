package edu.njit.cs.saboc.blu.core.gui.gep.panels;

import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayWidget;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.DiffTaxonomySubsetSelectionButton.DiffTaxonomySubsetCreationAction;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 *
 * @author Chris O
 */
public class DiffTaxonomySubsetSelectionPanel extends AbNDisplayWidget {
    
    private final Dimension panelSize = new Dimension(140, 28);
    
    private final DiffTaxonomySubsetSelectionButton optionBtn;
    
    public DiffTaxonomySubsetSelectionPanel(
            AbNDisplayPanel displayPanel, 
            DiffTaxonomySubsetCreationAction creationAction) {
        
        super(displayPanel);
        
        this.optionBtn = new DiffTaxonomySubsetSelectionButton(creationAction);
        
        this.setLayout(new BorderLayout());
        
        this.add(optionBtn, BorderLayout.CENTER);
    }
    
    @Override
    public void displayPanelResized(AbNDisplayPanel displayPanel) {
        
        super.displayPanelResized(displayPanel);
        
        this.setBounds(
                displayPanel.getWidth() - panelSize.width - 300, 
                displayPanel.getHeight() - panelSize.height - 30, 
                panelSize.width, 
                panelSize.height);
    }
}