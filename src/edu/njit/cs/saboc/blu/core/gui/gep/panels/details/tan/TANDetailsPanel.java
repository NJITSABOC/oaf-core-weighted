package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan;

import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn.AbNDetailsPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNContainerReportPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.AbNLevelReportPanel;
import edu.njit.cs.saboc.blu.core.gui.graphframe.buttons.PartitionedAbNSelectionPanel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

/**
 *
 * @author Chris O
 */
public class TANDetailsPanel extends AbNDetailsPanel<ClusterTribalAbstractionNetwork> {

    public TANDetailsPanel(TANConfiguration config) {
        super(config);

        AbNLevelReportPanel levelReportPanel = new AbNLevelReportPanel(config);
        levelReportPanel.displayAbNReport(config.getTribalAbstractionNetwork());

        AbNContainerReportPanel areaReportPanel = new AbNContainerReportPanel(config);
        areaReportPanel.displayAbNReport(config.getTribalAbstractionNetwork());
        
        PatriarchIntersectionReport intersectingPatriarchReport = new PatriarchIntersectionReport(config);
        intersectingPatriarchReport.displayAbNReport(config.getTribalAbstractionNetwork());

        super.addDetailsTab("Cluster TAN Levels", levelReportPanel);
        super.addDetailsTab("Bands in Cluster TAN", areaReportPanel);
        super.addDetailsTab("Patriarch Metrics", intersectingPatriarchReport);
        
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
