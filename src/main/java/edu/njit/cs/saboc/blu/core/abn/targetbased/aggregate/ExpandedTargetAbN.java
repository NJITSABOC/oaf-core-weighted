package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.ExpandedTargetAbNDerivation;

/**
 *
 * @author Chris O
 */
public class ExpandedTargetAbN extends TargetAbstractionNetwork
        implements SubAbstractionNetwork<TargetAbstractionNetwork> {

    private final TargetAbstractionNetwork aggregateSourceAbN;
    private final AggregateTargetGroup aggregateTargetGroup;

    public ExpandedTargetAbN(
            TargetAbstractionNetwork aggregateSourceAbN,
            AggregateTargetGroup aggregateTargetGroup) {

        super(aggregateSourceAbN.getFactory(),
                aggregateTargetGroup.getAggregatedHierarchy(),
                aggregateTargetGroup.getHierarchy(),
                new ExpandedTargetAbNDerivation(
                        aggregateSourceAbN.getDerivation(), 
                        aggregateTargetGroup.getRoot()));

        this.aggregateSourceAbN = aggregateSourceAbN;
        this.aggregateTargetGroup = aggregateTargetGroup;
    }

    public ExpandedTargetAbN(ExpandedTargetAbN expandedTAN) {
        this(expandedTAN.getSuperAbN(),
                expandedTAN.getAggregatePArea());
    }

    @Override
    public ExpandedTargetAbNDerivation getDerivation() {
        return (ExpandedTargetAbNDerivation) super.getDerivation();
    }

    @Override
    public TargetAbstractionNetwork getSuperAbN() {
        return aggregateSourceAbN;
    }

    public AggregateTargetGroup getAggregatePArea() {
        return aggregateTargetGroup;
    }
}
