package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.OverlappingNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A disjoint abstraction network created by selecting an overlapping 
 * singly rooted node and viewing all of its descendants.
 * 
 * @author Chris O
 * @param <T>
 * @param <PARENTABN_T>
 * @param <PARENTNODE_T>
 */
public class OverlappingNodeDisjointAbN<
        T extends DisjointNode<PARENTNODE_T>, 
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>, 
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>
       
        implements SubAbstractionNetwork<DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>> {

    private final DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN;

    private final PARENTNODE_T overlappingNode;

    public OverlappingNodeDisjointAbN(
            PARENTNODE_T overlappingNode,
            Hierarchy<T> subset,
            Hierarchy<Concept> sourceHierarchy,
            DisjointAbNFactory factory,
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN,
            OverlappingNodeDisjointAbNDerivation derivation) {

        super(sourceDisjointAbN.getParentAbstractionNetwork(),
                subset,
                sourceHierarchy,
                factory,
                sourceDisjointAbN.getLevelCount(),
                sourceDisjointAbN.getAllSourceNodes(),
                sourceDisjointAbN.getOverlappingNodes(),
                derivation);

        this.overlappingNode = overlappingNode;
        this.sourceDisjointAbN = sourceDisjointAbN;
    }

    @Override
    public OverlappingNodeDisjointAbNDerivation getDerivation() {
        return (OverlappingNodeDisjointAbNDerivation)super.getDerivation();
    }
    
    @Override
    public DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSuperAbN() {
        return sourceDisjointAbN;
    }

    @Override
    public SubsetDisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSubsetDisjointAbN(Set<PARENTNODE_T> overlaps) {
        return sourceDisjointAbN.getSubsetDisjointAbN(overlaps);
    }

    public PARENTNODE_T getSelectedSubset() {
        return overlappingNode;
    }
}
