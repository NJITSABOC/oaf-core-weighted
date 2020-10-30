package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.AbNHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.AbNDerivationExplanationPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.PAreaTaxonomyDerivationExplanation;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyHelpButton extends AbNHelpButton<PAreaTaxonomy> {

    public PAreaTaxonomyHelpButton(PAreaTaxonomyConfiguration config) {
        super(config);
    }

    @Override
    protected void displayDetailsWindow(AbNConfiguration config) {
        JDialog derivationExplanationDialog = new JDialog();
        derivationExplanationDialog.setModal(true);

        AbNDerivationExplanationPanel explanationPanel = new AbNDerivationExplanationPanel(
                new PAreaTaxonomyDerivationExplanation((PAreaTaxonomyConfiguration)config));
        
        derivationExplanationDialog.add(explanationPanel);
        derivationExplanationDialog.setSize(1200, 600);
        derivationExplanationDialog.setLocationRelativeTo(null);

        derivationExplanationDialog.setVisible(true);
    }
}
