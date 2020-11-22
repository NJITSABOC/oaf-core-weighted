package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.disjoint.DisjointSubjectSubtaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.gui.createanddisplayabn.CreateAndDisplayAbNThread;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.optionbuttons.node.NodeOptionButton;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateDisjointSubjectSubtaxonomyButton extends NodeOptionButton<Area> {
    
    private final PAreaTaxonomyConfiguration config;
    private final DisplayAbNAction<DisjointAbstractionNetwork> displayDisjointTaxonomyListener;
    
    public CreateDisjointSubjectSubtaxonomyButton(
            PAreaTaxonomyConfiguration config,
            DisplayAbNAction<DisjointAbstractionNetwork> displayDisjointTaxonomyListener) {
        
        super("BluSubjectDisjoint.png", 
                "<html>Create disjoint subject subtaxonomy <br>"
                + "(disjoint partial-area taxonomy that includes overlaps <br> "
                + "from outside of the current subhierarchy).");
        
        this.config = config;
        
        this.displayDisjointTaxonomyListener = displayDisjointTaxonomyListener;
        
        this.addActionListener((ae) -> {
            createAndDisplayDisjointSubtaxonomy();
        });
    }
    
    public final void createAndDisplayDisjointSubtaxonomy() {
        if (getCurrentNode().isPresent()) {
            CreateAndDisplayAbNThread display = new CreateAndDisplayAbNThread(
                    "Creating Disjoint Subject Subtaxonomy", 
                    displayDisjointTaxonomyListener) {

                @Override
                public AbstractionNetwork getAbN() {
                    return createDisjointSubtaxonomy();
                }
            };
                    
            display.startThread();
        }
    }

    @Override
    public void setEnabledFor(Area area) {
        DisjointSubjectSubtaxonomyGenerator generator = new DisjointSubjectSubtaxonomyGenerator();
        
        this.setEnabled(generator.canDeriveDisjointSubjectSubtaxonomy(config.getPAreaTaxonomy(), area));
    }
    
    private DisjointAbstractionNetwork createDisjointSubtaxonomy() {
        DisjointSubjectSubtaxonomyGenerator generator = new DisjointSubjectSubtaxonomyGenerator();
        
        return generator.createDisjointSubjectSubtaxonomy(config.getPAreaTaxonomy(), this.getCurrentNode().get());
    }
}
