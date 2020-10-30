package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Chris O
 */
public abstract class CreateTargetAbNFromPAreaButton extends NodeOptionButton<PArea> {
    
    public CreateTargetAbNFromPAreaButton() {
        
        super("BluTargetAbNFromPArea.png", "Create Target Abstraction Network from Partial-area");
        
        this.addActionListener( (ae) -> {
            showCreateTargetAbNMenu();
        });
    }

    @Override
    public void setEnabledFor(PArea parea) {
        this.setEnabled(!this.getUsableProperties(parea).isEmpty());
    }
    
    protected void showCreateTargetAbNMenu() {
        ArrayList<InheritableProperty> sortedProperties = new ArrayList<>(this.getUsableProperties(this.getCurrentNode().get()));
        sortedProperties.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        JPopupMenu availablePropertiesMenu = new JPopupMenu();

        sortedProperties.forEach( (property) -> {
            JMenuItem item = new JMenuItem(property.getName());
            item.addActionListener( (ae) -> {
                createAndDisplayTargetAbN(property);
            });

            availablePropertiesMenu.add(item);
        });

        availablePropertiesMenu.show(this, 20, 20);
    }
    
    protected abstract Set<InheritableProperty> getUsableProperties(PArea parea);
    
    protected abstract void createAndDisplayTargetAbN(InheritableProperty property);
}
