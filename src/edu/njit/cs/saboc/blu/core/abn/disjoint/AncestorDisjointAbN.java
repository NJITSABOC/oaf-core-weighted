package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A disjoint abstraction network that consists of a selected disjoint node
 * and all of its ancestor disjoint nodes.
 * 
 * @author Chris O
 * @param <T>
 * @param <PARENTABN_T>
 * @param <PARENTNODE_T>
 */
public class AncestorDisjointAbN <
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> extends DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> {
    
    private final T selectedRoot;
    
    private final DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN;
    
    public AncestorDisjointAbN(
            T selectedRoot,
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> sourceDisjointAbN,
            Hierarchy<T> nodeAncestorSubhierarchy,
            Hierarchy<Concept> sourceSubhierarchy,
            DisjointAbNDerivation derivation) {
        
        super(sourceDisjointAbN.getParentAbstractionNetwork(), 
                nodeAncestorSubhierarchy, 
                sourceSubhierarchy, 
                sourceDisjointAbN.getFactory(),
                sourceDisjointAbN.getLevelCount(), 
                sourceDisjointAbN.getAllSourceNodes(), 
                sourceDisjointAbN.getOverlappingNodes(),
                derivation);
        
        this.selectedRoot = selectedRoot;
        this.sourceDisjointAbN = sourceDisjointAbN;
        
        this.setAggregated(sourceDisjointAbN.isAggregated());
    }
        
    public T getSelectedRoot() {
        return selectedRoot;
    }
    
    public DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSourceDisjointAbN() {
        return sourceDisjointAbN;
    }
}
