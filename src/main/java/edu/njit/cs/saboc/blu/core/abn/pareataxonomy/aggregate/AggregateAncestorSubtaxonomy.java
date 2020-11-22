package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregateAncestorSubtaxonomyDerivation;

/**
 * An aggregate partial-area taxonomy that consists of a selected aggregate
 * partial-area and all of its aggregate partial-area ancestors
 * 
 * @author Chris O
 */
public class AggregateAncestorSubtaxonomy extends AncestorSubtaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
    
    private final AncestorSubtaxonomy nonAggregatedRootSubtaxonomy;
    
    private final AggregatedProperty ap;
    
    public AggregateAncestorSubtaxonomy(
            PAreaTaxonomy aggregatedSuperAbN, 
            AggregatedProperty aggregatedProperty,
            AggregatePArea selectedRoot,
            AncestorSubtaxonomy nonAggregatedRootSubtaxonomy,
            PAreaTaxonomy subtaxonomy) {
        
        super(aggregatedSuperAbN, 
                selectedRoot, 
                subtaxonomy, 
                new AggregateAncestorSubtaxonomyDerivation(
                        aggregatedSuperAbN.getDerivation(), 
                        aggregatedProperty, 
                        selectedRoot.getRoot()));
        
        this.nonAggregatedRootSubtaxonomy = nonAggregatedRootSubtaxonomy;
        this.ap = aggregatedProperty;
    }
    
    public AggregateAncestorSubtaxonomy(AggregateAncestorSubtaxonomy subtaxonomy) {
        this(subtaxonomy.getSuperAbN(), 
                subtaxonomy.getAggregatedProperty(),
                subtaxonomy.getSelectedRoot(), 
                subtaxonomy.getNonAggregateSourceAbN(), 
                subtaxonomy
                );
    }

    @Override
    public AncestorSubtaxonomy getNonAggregateSourceAbN() {
        return nonAggregatedRootSubtaxonomy;
    }

    @Override
    public int getAggregateBound() {
        return ap.getBound();
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
    
    @Override
    public PAreaTaxonomy expandAggregateNode(AggregatePArea parea) {
       return AggregatePAreaTaxonomy.generateExpandedSubtaxonomy(this, parea);
    }
    
    @Override
    public PAreaTaxonomy getAggregated(AggregatedProperty ap) {
        return AggregatePAreaTaxonomy.generateAggregatePAreaTaxonomy(this.getNonAggregateSourceAbN(), ap);
    }
    

    @Override
    public PAreaTaxonomy createRootSubtaxonomy(AggregatePArea root) {
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this, this.getNonAggregateSourceAbN(), root);
    }
    
    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSourceAbN(), this, source);
    }
    
    @Override
    public AggregatedProperty getAggregatedProperty(){
        return ap;
    }

    @Override
    public boolean isWeightedAggregated() {
        return this.ap.getWeighted();
    }
}
