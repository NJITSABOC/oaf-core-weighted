package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.CompactAbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

/**
 *
 * @author Chris O
 */
public class CompactPAreaTaxonomyDetailsPanel extends CompactAbNDetailsPanel<PAreaTaxonomy> {

    public CompactPAreaTaxonomyDetailsPanel(PAreaTaxonomyConfiguration config) {

        super(config);

        PartitionedAbNSelectionPanel abnTypeSelectionPanel = new PartitionedAbNSelectionPanel() {

            @Override
            public void showFullClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayPAreaTaxonomy(config.getPAreaTaxonomy());
            }

            @Override
            public void showBaseClicked() {
                config.getUIConfiguration().getAbNDisplayManager().displayAreaTaxonomy(config.getPAreaTaxonomy());
            }
        };

        abnTypeSelectionPanel.setBorder(BorderFactory.createTitledBorder("Partial-area Taxonomy Display Type"));
        
        abnTypeSelectionPanel.initialize(config, !config.getUIConfiguration().showingBaseAbN());

        this.add(abnTypeSelectionPanel, BorderLayout.SOUTH);
    }
}
