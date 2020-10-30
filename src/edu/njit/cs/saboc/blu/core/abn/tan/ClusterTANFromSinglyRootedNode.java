package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.TANFromSinglyRootedNodeDerivation;

/**
 * A TAN created from a singly rooted node in another abstraction network 
 * (e.g., a TAN created from a partial-area in a partial-area taxonomy,, or a 
 * TAN created from a disjoint partial-area in a disjoint partial-area 
 * taxonomy)
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class ClusterTANFromSinglyRootedNode<
        T extends SinglyRootedNode, 
        V extends AbstractionNetwork<T>>
        extends ClusterTribalAbstractionNetwork<Cluster> {
    
    private final V parentAbN;
    private final T sourceNode;
    
    public ClusterTANFromSinglyRootedNode(
            ClusterTribalAbstractionNetwork tan, 
            V parentAbN, 
            T sourceNode) {
        
        super(tan, 
                new TANFromSinglyRootedNodeDerivation(
                        parentAbN.getDerivation(), 
                        tan.getSourceFactory(), 
                        sourceNode.getRoot()));
        
        this.parentAbN = parentAbN;
        this.sourceNode = sourceNode;
    }
    
    public ClusterTANFromSinglyRootedNode(ClusterTANFromSinglyRootedNode<T, V> tan) {
        this(tan, tan.getParentAbN(), tan.getSourceNode());
    }
    
    @Override
    public TANFromSinglyRootedNodeDerivation getDerivation() {
        return (TANFromSinglyRootedNodeDerivation)super.getDerivation();
    }
    
    public V getParentAbN() {
        return parentAbN;
    }
    
    public T getSourceNode() {
        return sourceNode;
    }
}
