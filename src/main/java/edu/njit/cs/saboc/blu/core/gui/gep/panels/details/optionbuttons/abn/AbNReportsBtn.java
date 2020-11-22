package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.abn;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;

/**
 *
 * @author Chris O
 * @param <T>
 */
public abstract class AbNReportsBtn<T extends AbstractionNetwork> extends AbNOptionsButton<T> {
    
    public AbNReportsBtn() {
        
        super("BluReportBtn.png", "View Reports and Metrics");
        
        this.addActionListener( (ae) -> {
            displayReportsAndMetrics();
        });
    }
    
    public abstract void displayReportsAndMetrics();
}
