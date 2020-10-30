package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 * 
 * @param <ABN_T>
 */
public class CompactAbNDetailsPanel<ABN_T extends AbstractionNetwork> extends JPanel {
    
    private final AbNConfiguration config;
    
    private final GenericAbNSummaryPanel<ABN_T> summaryPanel;
    
    public CompactAbNDetailsPanel(AbNConfiguration config) {
        this.config = config;
        
        this.summaryPanel = new GenericAbNSummaryPanel<>(config);
        
        this.setLayout(new BorderLayout());
        
        this.add(summaryPanel, BorderLayout.CENTER);
    }
}
