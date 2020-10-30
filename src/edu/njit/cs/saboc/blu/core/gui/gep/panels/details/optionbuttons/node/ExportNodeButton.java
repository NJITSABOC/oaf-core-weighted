package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import java.awt.event.ActionEvent;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class ExportNodeButton<T extends Node> extends NodeOptionButton<T> {
    public ExportNodeButton(String toolTip) {
        super("BluExport.png", toolTip);
        
        this.addActionListener((ActionEvent ae) -> {
            exportAction();
        });
    }
    
    @Override
    public void setEnabledFor(T node) {
        this.setEnabled(true);
    }
    
    public abstract void exportAction();
}