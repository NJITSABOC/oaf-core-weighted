package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * A generator class for creating target abstraction networks.
 * 
 * @author Chris 
 */
public class AggregateTargetAbNGenerator {
    
    /**
     * Creates an aggregate target abstraction network with the given bound
     * 
     * @param sourceTargetAbN
     * @param generator
     * @param aggregateGenerator
     * @param aggregatedProperty which includes bound and weightedAggregated
     * @return 
     */
    public TargetAbstractionNetwork createAggregateTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            TargetAbstractionNetworkGenerator generator,
            AggregateAbNGenerator<TargetGroup, AggregateTargetGroup> aggregateGenerator,
            AggregatedProperty aggregatedProperty) {

        if (aggregatedProperty.getBound() == 1 && aggregatedProperty.getAutoScaled() ==false) {
            return sourceTargetAbN;
        }
        
        Hierarchy<AggregateTargetGroup> reducedTargetHierarchy = aggregateGenerator.createAggregateAbN(
                        new AggregateTargetAbNFactory(),
                        sourceTargetAbN.getTargetGroupHierarchy(),
                        sourceTargetAbN.getSourceHierarchy(),
                        aggregatedProperty);
        
        TargetAbstractionNetwork targetAbN = new AggregateTargetAbN(
                sourceTargetAbN, 
                aggregatedProperty,
                reducedTargetHierarchy, 
                sourceTargetAbN.getSourceHierarchy());

        return targetAbN;
    }
    
    public ExpandedTargetAbN createExpandedTargetAbN(
            TargetAbstractionNetwork sourceAggregateTargetAbN,
            AggregateTargetGroup aggregateDisjointNode) {

        ExpandedTargetAbN expandedDisjointAbN = new ExpandedTargetAbN(
                sourceAggregateTargetAbN,
                aggregateDisjointNode);

        return expandedDisjointAbN;
    } 
}
