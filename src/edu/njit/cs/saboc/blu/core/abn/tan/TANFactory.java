package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

/**
 * A factory class for creating TAN objects. Can be overridden when 
 * implementation-specific details need to be stored in the TAN or 
 * used to derive the TAN
 * 
 * @author Chris O
 */
public class TANFactory {
    
    private final Ontology sourceOntology;
    
    public TANFactory(Ontology sourceOntology) {
        this.sourceOntology = sourceOntology;
    }
    
    public Ontology getSourceOntology() {
        return sourceOntology;
    }
    
    public BandTribalAbstractionNetwork createBandTAN(
            Hierarchy<Band> bandHierarchy, 
            Hierarchy<Concept> sourceHierarchy) {
        
        return new BandTribalAbstractionNetwork(
                this, 
                bandHierarchy, 
                sourceHierarchy);
    }
    
    public <T extends Cluster> ClusterTribalAbstractionNetwork createClusterTAN(
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {
        
        return new ClusterTribalAbstractionNetwork<>(
                bandTAN, 
                clusterHierarchy, 
                sourceHierarchy);
    }
    
    public <T extends Cluster, V extends SinglyRootedNode, U extends AbstractionNetwork<V>> ClusterTribalAbstractionNetwork createTANFromSinglyRootedNode(
            ClusterTribalAbstractionNetwork<T> theTAN,
            V node,
            U sourceAbN) {
        
        return new ClusterTANFromSinglyRootedNode(theTAN, sourceAbN, node);
    }
    
    public <T extends Cluster, V extends PartitionedNode, U extends PartitionedAbstractionNetwork> ClusterTribalAbstractionNetwork createTANFromPartitionedNode(
            ClusterTribalAbstractionNetwork<T> theTAN,
            V node,
            U sourceAbN) {
        
        return new ClusterTANFromPartitionedNode(theTAN, sourceAbN, node);
    }
}
