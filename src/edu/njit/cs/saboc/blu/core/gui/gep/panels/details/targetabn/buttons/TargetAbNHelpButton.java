package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetabn.buttons;

import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn.AbNHelpButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.targetbased.configuration.TargetAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.AbNDerivationExplanationPanel;
import edu.njit.cs.saboc.blu.core.gui.panels.derivationexplanation.TargetAbNDerivationExplanation;
import javax.swing.JDialog;

/**
 *
 * @author Chris O
 */
public class TargetAbNHelpButton extends AbNHelpButton<TargetAbstractionNetwork> {

    public TargetAbNHelpButton(TargetAbNConfiguration config) {
        super(config);
    }

    @Override
    protected void displayDetailsWindow(AbNConfiguration config) {
        JDialog derivationExplanationDialog = new JDialog();
        derivationExplanationDialog.setModal(true);

        AbNDerivationExplanationPanel explanationPanel = new AbNDerivationExplanationPanel(
                new TargetAbNDerivationExplanation((TargetAbNConfiguration)config));
        
        derivationExplanationDialog.add(explanationPanel);
        derivationExplanationDialog.setSize(1200, 600);
        derivationExplanationDialog.setLocationRelativeTo(null);

        derivationExplanationDialog.setVisible(true);
    }
}
