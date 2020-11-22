package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.buttons;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.AbNHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.AbNDerivationExplanationPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.TANDerivationExplanation;
import javax.swing.JDialog;

/**
 *
 * @author Chris Ochs
 */
public class TANHelpButton extends AbNHelpButton<ClusterTribalAbstractionNetwork> {

    public TANHelpButton(TANConfiguration config) {
        super(config);
    }

    @Override
    protected void displayDetailsWindow(AbNConfiguration config) {
        
        JDialog derivationExplanationDialog = new JDialog();
        derivationExplanationDialog.setModal(true);

        AbNDerivationExplanationPanel explanationPanel = new AbNDerivationExplanationPanel(
                new TANDerivationExplanation((TANConfiguration)config));
        
        derivationExplanationDialog.add(explanationPanel);
        derivationExplanationDialog.setSize(1200, 750);
        derivationExplanationDialog.setLocationRelativeTo(null);

        derivationExplanationDialog.setVisible(true);
    }
}
