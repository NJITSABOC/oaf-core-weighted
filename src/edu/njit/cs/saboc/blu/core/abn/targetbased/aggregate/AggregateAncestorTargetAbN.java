package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.targetbased.AncestorTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AggregateAncestorTargetAbNDerivation;

/**
 *
 * @author Chris Ochs
 */
public class AggregateAncestorTargetAbN extends AncestorTargetAbN<AggregateTargetGroup> 
        implements AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> {
    
    private final TargetAbstractionNetwork nonAggregateSourceTargetAbN;
    
    private final AggregatedProperty ap;
    
    public AggregateAncestorTargetAbN(
            TargetAbstractionNetwork aggregateSourceTAN, 
            AggregateTargetGroup sourceGroup,
            AggregatedProperty aggregatedProperty,
            TargetAbstractionNetwork nonAggregateSourceTargetAbN,
            TargetAbstractionNetwork subTAN) {
        
        super(aggregateSourceTAN, 
                sourceGroup, 
                subTAN.getTargetGroupHierarchy(),
                subTAN.getSourceHierarchy(), 
                new AggregateAncestorTargetAbNDerivation(
                        aggregateSourceTAN.getDerivation(), 
                        aggregatedProperty,
                        sourceGroup.getRoot()));
        
        this.ap = aggregatedProperty;
        
        this.nonAggregateSourceTargetAbN = nonAggregateSourceTargetAbN;
        
        this.setAggregated(true);
    }
    
    public AggregateAncestorTargetAbN(AggregateAncestorTargetAbN subTAN) {
        
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
                subTAN.getAggregatedProperty(),
                subTAN.getNonAggregateSourceAbN(), 
                subTAN);
    }

    @Override
    public TargetAbstractionNetwork getNonAggregateSourceAbN() {
        return nonAggregateSourceTargetAbN;
    }
    
    @Override
    public int getAggregateBound() {
        return ap.getBound();
    }
    
    @Override
    public boolean isWeightedAggregated() {
        return this.ap.getWeighted();
    }

    @Override
    public boolean isAggregated() {
        return true;
    }
        
    @Override
    public TargetAbstractionNetwork getAggregated(AggregatedProperty ap) {
        return AggregateTargetAbN.createAggregated(this.getNonAggregateSourceAbN(), ap);
    }

    @Override
    public TargetAbstractionNetwork expandAggregateNode(AggregateTargetGroup group) {        
        return new AggregateTargetAbNGenerator().createExpandedTargetAbN(this, group);
    }
    
    @Override
    public AggregateAncestorTargetAbN createAncestorTargetAbN(AggregateTargetGroup source) {
        return AggregateTargetAbN.createAggregateAncestorTargetAbN(
                this.getNonAggregateSourceAbN(), 
                this, 
                source);
    }

    @Override
    public AggregateDescendantTargetAbN createDescendantTargetAbN(AggregateTargetGroup root) {
        return AggregateTargetAbN.createAggregateDescendantTargetAbN(
                this.getNonAggregateSourceAbN(),
                this,
                root);
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return ap;
    }
}