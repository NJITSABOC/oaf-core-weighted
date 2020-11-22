
package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class AbNReportPanel<T extends AbstractionNetwork> extends JPanel {
    
    private final AbNConfiguration config;
    
    public AbNReportPanel(AbNConfiguration config) {
        this.config = config;
    }
    
    public AbNConfiguration getConfiguration() {
        return config;
    }
    
    public abstract void displayAbNReport(T abn);
}
