
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

/**
 *
 * @author Chris O
 */
public class CompactTANDetailsPanel extends CompactAbNDetailsPanel<ClusterTribalAbstractionNetwork> {
    
    public CompactTANDetailsPanel(TANConfiguration config) {
        super(config);

        PartitionedAbNSelectionPanel abnTypeSelectionPanel = new PartitionedAbNSelectionPanel() {

            @Override
            public void showFullClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayTribalAbstractionNetwork(config.getAbstractionNetwork());
            }

            @Override
            public void showBaseClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayBandTribalAbstractionNetwork(config.getAbstractionNetwork());
            }
        };

        abnTypeSelectionPanel.setBorder(BorderFactory.createTitledBorder("Tribal Abstraction Network Display Type"));

        abnTypeSelectionPanel.initialize(config, !config.getUIConfiguration().showingBaseAbN());

        this.add(abnTypeSelectionPanel, BorderLayout.SOUTH);
    }
    
}
