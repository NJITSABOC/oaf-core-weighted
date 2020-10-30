package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOptionsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.buttons.ViewMatchingDiffAreaButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris Ochs
 */
public class DiffAreaOptionsPanel extends NodeOptionsPanel {

    public DiffAreaOptionsPanel(DiffPAreaTaxonomyConfiguration config) {
        
        ViewMatchingDiffAreaButton viewMatchingDiffPAreaBtn = new ViewMatchingDiffAreaButton(config);
        
        super.addOptionButton(viewMatchingDiffPAreaBtn);
        
        
        NodeHelpButton helpBtn = new NodeHelpButton(config);

        super.addOptionButton(helpBtn);
    }
}