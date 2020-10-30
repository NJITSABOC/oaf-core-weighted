package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SubsetDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A disjoint abstraction network created by selecting a set of overlapping singly rooted nodes.
 * 
 * The disjoint abstraction network consists of all of the disjoint nodes that overlap between 
 * the selected singly rooted nodes or a subset of the singly rooted nodes.
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <PARENTABN_T>
 * @param <PARENTNODE_T>
 */
public class SubsetDisjointAbstractionNetwork<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> 
            implements SubAbstractionNetwork<DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>> {
    
    private final DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN;
    
    private final Set<PARENTNODE_T> selectedSubset;
    
    public SubsetDisjointAbstractionNetwork(
            Set<PARENTNODE_T> selectedSubset,
            Hierarchy<T> subset,
            Hierarchy<Concept> sourceHierarchy, 
            DisjointAbNFactory factory,
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN,
            SubsetDisjointAbNDerivation derivation) {
        
        super(sourceDisjointAbN.getParentAbstractionNetwork(), 
                subset, 
                sourceHierarchy, 
                factory,
                sourceDisjointAbN.getLevelCount(), 
                sourceDisjointAbN.getAllSourceNodes(),
                sourceDisjointAbN.getOverlappingNodes(), 
                derivation);
        
        this.selectedSubset = selectedSubset;
        this.sourceDisjointAbN = sourceDisjointAbN;
    }
    
    @Override
    public SubsetDisjointAbNDerivation getDerivation() {
        return (SubsetDisjointAbNDerivation)super.getDerivation();
    }

    @Override
    public DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSuperAbN() {
        return sourceDisjointAbN;
    }
    
    @Override
    public SubsetDisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSubsetDisjointAbN(Set<PARENTNODE_T> overlaps) {
        return sourceDisjointAbN.getSubsetDisjointAbN(overlaps);
    }
    
    public  Set<PARENTNODE_T> getSelectedSubset() {
        return selectedSubset;
    }
}
