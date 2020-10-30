package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public abstract class CreateSubtaxonomyButton extends NodeOptionButton<PArea> {
    
    private final DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener;
    
    public CreateSubtaxonomyButton(
            String iconName,
            String toolTip,
            DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener) {
        
        super(iconName, toolTip);
        
        this.displayTaxonomyListener = displayTaxonomyListener;
        
        this.addActionListener((ae) -> {
            createAndDisplaySubtaxonomy();
        });
    }
    
    public final void createAndDisplaySubtaxonomy() {
        if (getCurrentNode().isPresent()) {
            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread("Creating Subtaxonomy", displayTaxonomyListener) {

                @Override
                public AbstractionNetwork getAbN() {
                    return createSubtaxonomy();
                }
            };
                    
            display.startThread();
        }
    }
    
    public abstract PAreaTaxonomy createSubtaxonomy();
}
