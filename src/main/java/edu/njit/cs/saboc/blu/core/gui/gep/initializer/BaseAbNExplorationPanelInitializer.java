
package edu.njit.cs.saboc.blu.core.gui.gep.initializer;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDashboardPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNDisplayPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.AbNInitialDisplayAction;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.NavigationPanel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.ViewportNavigationListener;
import edu.njit.cs.saboc.blu.core.gui.gep.warning.AbNWarningManager;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris O
 */
public class BaseAbNExplorationPanelInitializer implements AbNExplorationPanelGUIInitializer {
    
    private final AbNWarningManager warningManager;
    
    public BaseAbNExplorationPanelInitializer(AbNWarningManager warningManager) {
        this.warningManager = warningManager;
    }
    
    public AbNWarningManager getWarningManager() {
        return warningManager;
    }

    @Override
    public void initializeAbNDisplayPanel(AbNDisplayPanel displayPanel, boolean startUp) {
        NavigationPanel navigationPanel = new NavigationPanel(displayPanel);
        navigationPanel.addNavigationPanelListener(new ViewportNavigationListener(displayPanel));
        
        displayPanel.addZoomFactorChangedListener( (zoomFactor) -> {
            navigationPanel.setZoomLevel(zoomFactor);
        });
        
        displayPanel.addWidget(navigationPanel);
        
        if (startUp) {
            // Do first time startup stuff
        }
    }

    @Override
    public void initializeAbNDDashboardPanel(AbNDashboardPanel displayPanel) {
        
    }

    @Override
    public AbNInitialDisplayAction getInitialDisplayAction() {
        return new AbNInitialDisplayAction();
    }

    @Override
    public void showAbNAlerts(AbNDisplayPanel displayPanel) {
        AbstractionNetwork abn = displayPanel.getGraph().getAbstractionNetwork();
        
        if (warningManager != null && warningManager.showAggregationWarning()) {

            if (abn instanceof AggregateableAbstractionNetwork) {

                if (abn.getNodeCount() >= 1000) {

                    Thread waitThread = new Thread(() -> {

                        try {

                            Thread.sleep(1000);

                        } catch (InterruptedException ie) {

                        }

                        SwingUtilities.invokeLater(() -> {
                            
                            String message = "<html>This abstraction network is relatively large."
                                    + "<p><p>To reduce its size use the aggregation slider at the bottom right. "
                                    + "<p>This will hide smaller nodes, simplifying the display.";
                            
                            String [] options = {"Warn me again", "Do not warn me again"};
                            
                            int result = JOptionPane.showOptionDialog(
                                    displayPanel, 
                                    message, 
                                    "Large Abstraction Network", 
                                    JOptionPane.YES_NO_OPTION, 
                                    JOptionPane.INFORMATION_MESSAGE,
                                    null,
                                    options,
                                    options[0]);
                            
                            if(result == 1) {
                                warningManager.setShowAggregationWarning(false);
                            }
                        });
                    });

                    waitThread.start();
                }
            }
        }
    }
}
