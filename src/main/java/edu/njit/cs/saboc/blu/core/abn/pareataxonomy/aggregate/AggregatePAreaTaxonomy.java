package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AncestorSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.RootSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AggregatePAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A partial-area taxonomy where all of the partial-areas smaller than a given 
 * bound are summarized by aggregate partial-areas
 * 
 * @author Chris O
 */
public class AggregatePAreaTaxonomy extends PAreaTaxonomy<AggregatePArea> 
        implements AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy> {
           
    public static final PAreaTaxonomy generateAggregatePAreaTaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy, 
            AggregatedProperty aggregatedProperty) {
        
        AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createAggregatePAreaTaxonomy(
                nonAggregateTaxonomy, 
                new PAreaTaxonomyGenerator(),
                new AggregateAbNGenerator<>(),
                aggregatedProperty);
    }
    
    
    public static final PAreaTaxonomy generateAggregateRootSubtaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy,
            PAreaTaxonomy superAggregateTaxonomy,
            AggregatePArea selectedRoot) {
        
        RootSubtaxonomy nonAggregateRootSubtaxonomy = (RootSubtaxonomy)nonAggregateTaxonomy.createRootSubtaxonomy(
                selectedRoot.getAggregatedHierarchy().getRoot());
        
        
        AggregatedProperty ap = ((AggregateAbstractionNetwork<AggregatePArea, PAreaTaxonomy>)superAggregateTaxonomy).getAggregatedProperty();
        
        PAreaTaxonomy aggregateRootSubtaxonomy = nonAggregateRootSubtaxonomy.getAggregated(ap);
        
        return new AggregateRootSubtaxonomy(
                superAggregateTaxonomy,
                ap,
                nonAggregateRootSubtaxonomy, 
                aggregateRootSubtaxonomy
        );
    }
    
    public static final PAreaTaxonomy generateAggregateAncestorSubtaxonomy(
            PAreaTaxonomy nonAggregateTaxonomy,
            PAreaTaxonomy superAggregateTaxonomy,
            AggregatePArea selectedRoot) {
        
        Hierarchy<PArea> actualPAreaHierarchy = AbstractionNetworkUtils.getDeaggregatedAncestorHierarchy(
                nonAggregateTaxonomy.getPAreaHierarchy(), 
                superAggregateTaxonomy.getPAreaHierarchy().getAncestorHierarchy(selectedRoot));
        
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(
                actualPAreaHierarchy, 
                nonAggregateTaxonomy.getSourceHierarchy());
        
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();
        PAreaTaxonomy nonAggregatedAncestorSubtaxonomy = generator.createTaxonomyFromPAreas(
                nonAggregateTaxonomy.getPAreaTaxonomyFactory(), 
                actualPAreaHierarchy, 
                sourceHierarchy);

        AggregatedProperty ap = ((AggregateAbstractionNetwork)superAggregateTaxonomy).getAggregatedProperty();
        
        // Convert to ancestor subhierarchy
        AncestorSubtaxonomy subtaxonomy = new AncestorSubtaxonomy(
                nonAggregateTaxonomy,
                selectedRoot.getAggregatedHierarchy().getRoot(),
                nonAggregatedAncestorSubtaxonomy); 
        
        PAreaTaxonomy aggregateAncestorSubtaxonomy = subtaxonomy.getAggregated(ap);
        
        return new AggregateAncestorSubtaxonomy(
                superAggregateTaxonomy,
                ap,
                selectedRoot,
                subtaxonomy, 
                aggregateAncestorSubtaxonomy);
    }
    
    public static final ExpandedSubtaxonomy generateExpandedSubtaxonomy(
            PAreaTaxonomy aggregateTaxonomy, 
            AggregatePArea parea) {
        
         AggregatePAreaTaxonomyGenerator generator = new AggregatePAreaTaxonomyGenerator();
        
        return generator.createExpandedSubtaxonomy(aggregateTaxonomy, parea, new PAreaTaxonomyGenerator());
    }
    
    private final PAreaTaxonomy nonAggregateBaseTaxonomy;
    
    private final AggregatedProperty ap;
    
    public AggregatePAreaTaxonomy(
            PAreaTaxonomy nonAggregateBaseTaxonomy,
            AggregatedProperty aggregatedProperty,
            PAreaTaxonomy aggregatedPAreaTaxonomy) {
    
        super(aggregatedPAreaTaxonomy.getAreaTaxonomy(), 
                aggregatedPAreaTaxonomy.getPAreaHierarchy(), 
                nonAggregateBaseTaxonomy.getSourceHierarchy(), 
                new AggregatePAreaTaxonomyDerivation(nonAggregateBaseTaxonomy.getDerivation(), aggregatedProperty));
        
        this.nonAggregateBaseTaxonomy = nonAggregateBaseTaxonomy;
        this.ap = aggregatedProperty;
    }
    
    @Override
    public AggregatePAreaTaxonomyDerivation getDerivation() {
        return (AggregatePAreaTaxonomyDerivation)super.getDerivation();
    }
    
    @Override
    public PAreaTaxonomy getNonAggregateSourceAbN() {
        return nonAggregateBaseTaxonomy;
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
        return AggregatePAreaTaxonomy.generateAggregateRootSubtaxonomy(this.getNonAggregateSourceAbN(), this, root);
    }

    @Override
    public PAreaTaxonomy createAncestorSubtaxonomy(AggregatePArea source) {
        return AggregatePAreaTaxonomy.generateAggregateAncestorSubtaxonomy(this.getNonAggregateSourceAbN(), this, source);
    }
    
    @Override
    public AggregatedProperty getAggregatedProperty(){
        return this.ap;
    }

    @Override
    public boolean isWeightedAggregated() {
        return this.ap.getWeighted();
    }
}