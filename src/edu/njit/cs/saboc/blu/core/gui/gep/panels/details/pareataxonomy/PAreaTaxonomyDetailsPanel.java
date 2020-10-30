
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNContainerReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNLevelReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate.AggregateAbNLevelReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.aggregate.AggregateContainerReportPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

/**
 *
 * @author Chris O
 */
public class PAreaTaxonomyDetailsPanel extends AbNDetailsPanel<PAreaTaxonomy> {
    
    public PAreaTaxonomyDetailsPanel(PAreaTaxonomyConfiguration config) {
        super(config);

        if (config.getPAreaTaxonomy().isAggregated()) {
            AggregateAbNLevelReportPanel levelReportPanel = new AggregateAbNLevelReportPanel(config);
            levelReportPanel.displayAbNReport(config.getPAreaTaxonomy());

            AggregateContainerReportPanel areaReportPanel = new AggregateContainerReportPanel(config);
            areaReportPanel.displayAbNReport(config.getPAreaTaxonomy());

            super.addDetailsTab("Aggregate Partial-area Taxonomy Levels", levelReportPanel);
            super.addDetailsTab("Areas in Aggregate Partial-area Taxonomy", areaReportPanel);
        } else {
            AbNLevelReportPanel levelReportPanel = new AbNLevelReportPanel(config);
            levelReportPanel.displayAbNReport(config.getPAreaTaxonomy());

            AbNContainerReportPanel areaReportPanel = new AbNContainerReportPanel(config);
            areaReportPanel.displayAbNReport(config.getPAreaTaxonomy());

            super.addDetailsTab("Partial-area Taxonomy Levels", levelReportPanel);
            super.addDetailsTab("Areas in Partial-area Taxonomy", areaReportPanel);
        }
        
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