package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 */
public abstract class OpenBrowserButton<T extends SinglyRootedNode> extends NodeOptionButton<T> {
    
    public OpenBrowserButton(String nodeType) {
        super(
                "BluInvestigateRoot.png", 
                String.format("View %s root in concept browser.", nodeType.toLowerCase()));
        
        this.addActionListener((ae) -> {
            displayBrowserWindowAction();
        });
    }
    
    @Override
    public void setEnabledFor(T entity) {
        this.setEnabled(true);
    }
    
    public abstract void displayBrowserWindowAction();
}
