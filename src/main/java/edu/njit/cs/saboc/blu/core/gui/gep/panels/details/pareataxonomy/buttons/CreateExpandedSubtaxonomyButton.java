package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.buttons;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.listener.DisplayAbNAction;

/**
 *
 * @author Chris O
 */
public class CreateExpandedSubtaxonomyButton extends CreateSubtaxonomyButton {
    
    private final PAreaTaxonomyConfiguration config;
    
    public CreateExpandedSubtaxonomyButton(PAreaTaxonomyConfiguration config, 
            DisplayAbNAction<PAreaTaxonomy> displayTaxonomyListener) {
        
        super("BluExpandedSubtaxonomy.png", 
                "Expand aggregate partial-area", 
                displayTaxonomyListener);
        
        this.config = config;
    }

    @Override
    public PAreaTaxonomy createSubtaxonomy() {
        AggregatePArea parea = (AggregatePArea)super.getCurrentNode().get();
        
        AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> taxonomy = (AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)config.getPAreaTaxonomy();
        
        return taxonomy.expandAggregateNode(parea);
    }

    @Override
    public void setEnabledFor(PArea parea) {
        AggregatePArea aggregatePArea = (AggregatePArea)parea;
        
        this.setEnabled(!aggregatePArea.getAggregatedNodes().isEmpty());
    }
}