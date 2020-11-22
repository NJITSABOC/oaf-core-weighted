package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate.AggregatePArea;
import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.ExpandedSubtaxonomyDerivation;

/**
 * A partial-area taxonomy created by expanding the subhierarchy of partial-areas 
 * summarized by a selected aggregate partial-area from an aggregate partial-area 
 * taxonomy.
 * 
 * @author Chris O
 * @param <T>
 */
public class ExpandedSubtaxonomy<T extends PAreaTaxonomy & AggregateAbstractionNetwork> 
            extends PAreaTaxonomy implements SubAbstractionNetwork<T> {
    
    private final T superTaxonomy;
    private final AggregatePArea aggregatePArea;
    
    public ExpandedSubtaxonomy(
            T superTaxonomy,
            AggregatePArea aggregatePArea,
            PAreaTaxonomy expandedSubtaxonomy) {

        super(expandedSubtaxonomy, new ExpandedSubtaxonomyDerivation(superTaxonomy.getDerivation(), aggregatePArea.getRoot()));
        
        this.superTaxonomy = superTaxonomy;
        this.aggregatePArea = aggregatePArea;
    }
    
    @Override
    public ExpandedSubtaxonomyDerivation getDerivation() {
        return (ExpandedSubtaxonomyDerivation)super.getDerivation();
    }

    @Override
    public T getSuperAbN() {
        return superTaxonomy;
    }

    public AggregatePArea getExpandedAggregatePArea() {
        return aggregatePArea;
    }
}