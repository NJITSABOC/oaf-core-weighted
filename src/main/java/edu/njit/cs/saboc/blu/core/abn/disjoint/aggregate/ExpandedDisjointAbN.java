package edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.ExpandedDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * Represents a disjoint abstraction network created by expanding the hierarchy of
 * disjoint nodes summarized by a selected aggregate disjoint node.
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <PARENTABN_T>
 * @param <PARENTNODE_T>
 */
public class ExpandedDisjointAbN<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> {
    
    private final DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceAbN;
    private final AggregateDisjointNode<PARENTNODE_T> aggregatedNode;
    
    public ExpandedDisjointAbN(
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceAbN, 
            AggregateDisjointNode<PARENTNODE_T> aggregatedNode) {
        
        super(sourceAbN.getParentAbstractionNetwork(), 
                (Hierarchy<T>)aggregatedNode.getAggregatedHierarchy(), 
                aggregatedNode.getHierarchy(), 
                sourceAbN.getFactory(),
                sourceAbN.getLevelCount(), 
                sourceAbN.getAllSourceNodes(),
                sourceAbN.getOverlappingNodes(), 
                new ExpandedDisjointAbNDerivation(
                        sourceAbN.getDerivation(), 
                        aggregatedNode.getRoot()));
        
        this.sourceAbN = sourceAbN;
        this.aggregatedNode = aggregatedNode;
    }
    
    @Override
    public ExpandedDisjointAbNDerivation getDerivation() {
        return (ExpandedDisjointAbNDerivation)super.getDerivation();
    }
    
    public  DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSourceAggregateDisjointAbN() {
        return sourceAbN;
    }
    
    public AggregateDisjointNode<PARENTNODE_T> getAggregatedNode() {
        return aggregatedNode;
    }
}
