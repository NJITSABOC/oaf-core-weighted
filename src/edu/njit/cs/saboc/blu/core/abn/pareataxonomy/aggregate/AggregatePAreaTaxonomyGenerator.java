package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.ExpandedSubtaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * A generator class for creating aggregate partial-area taxonomy objects
 * 
 * @author Chris O
 */
public class AggregatePAreaTaxonomyGenerator {
        
    /**
     * Creates an aggregate partial-area taxonomy with the chosen bound
     * 
     * @param sourceTaxonomy
     * @param generator
     * @param aggregateGenerator
     * @param aggregatedProperty which includes min and isWeightedAggregated
     * @return 
     */
    public PAreaTaxonomy createAggregatePAreaTaxonomy(
            final PAreaTaxonomy sourceTaxonomy,
            final PAreaTaxonomyGenerator generator,
            final AggregateAbNGenerator<PArea, AggregatePArea> aggregateGenerator, 
            final AggregatedProperty aggregatedProperty) {
        
        if(aggregatedProperty.getBound() == 1 && aggregatedProperty.getAutoScaled()==false) {
            return sourceTaxonomy;
        }
        
        Hierarchy<AggregatePArea> reducedPAreaHierarchy = 
                aggregateGenerator.createAggregateAbN(
                        new AggregatePAreaTaxonomyFactory(),
                        sourceTaxonomy.getPAreaHierarchy(), 
                        sourceTaxonomy.getSourceHierarchy(),
                        aggregatedProperty);

        Hierarchy<PArea> pareaHierarchy = (Hierarchy<PArea>)(Hierarchy<?>)reducedPAreaHierarchy;

        PAreaTaxonomy aggregatedTaxonomy = generator.createTaxonomyFromPAreas(
                sourceTaxonomy.getPAreaTaxonomyFactory(), 
                pareaHierarchy, 
                sourceTaxonomy.getSourceHierarchy());
        
        
                       
        return new AggregatePAreaTaxonomy(
                sourceTaxonomy, 
                aggregatedProperty,
                aggregatedTaxonomy);
    }
   
    /**
     * Creates an expanded subtaxonomy from the given aggregate partial-area
     * 
     * @param sourceAggregateTaxonomy
     * @param aggregatePArea
     * @param generator
     * @return 
     */
    public ExpandedSubtaxonomy createExpandedSubtaxonomy(
            PAreaTaxonomy sourceAggregateTaxonomy,
            AggregatePArea aggregatePArea, 
            PAreaTaxonomyGenerator generator) {
        
        PAreaTaxonomy expandedSubtaxonomy = generator.createTaxonomyFromPAreas(
                sourceAggregateTaxonomy.getPAreaTaxonomyFactory(), 
                aggregatePArea.getAggregatedHierarchy(),
                sourceAggregateTaxonomy.getSourceHierarchy());
        
        return new ExpandedSubtaxonomy(
                sourceAggregateTaxonomy, 
                aggregatePArea, 
                expandedSubtaxonomy);
    }
}
