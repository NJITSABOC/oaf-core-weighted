package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.node.OverlappingConceptDetails;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Represents an abstraction network that partitions another abstraction network.
 * 
 * The singly rooted nodes of a partitioned abstraction network are separated into 
 * "containers" or "partitioned nodes" based on some structural criteria.
 * 
 * For example, in a partial-area taxonomy, partial-areas are separated into
 * areas (in the OAF system, the inverse is true in the papers describing taxonomies)
 * 
 * @author Chris O
 * @param <NODE_T>
 * @param <BASENODE_T>
 */
public abstract class PartitionedAbstractionNetwork<
        NODE_T extends SinglyRootedNode, 
        BASENODE_T extends PartitionedNode> extends AbstractionNetwork<NODE_T> {
    
    private final AbstractionNetwork<BASENODE_T> baseAbstractionNetwork;
    
    public PartitionedAbstractionNetwork(
        AbstractionNetwork<BASENODE_T> baseAbstractionNetwork,
        Hierarchy<NODE_T> partitionNodeHierarchy, 
        Hierarchy<Concept> sourceHierarchy, 
        AbNDerivation derivation) {
        
        super(partitionNodeHierarchy, sourceHierarchy, derivation);
        
        this.baseAbstractionNetwork = baseAbstractionNetwork;
    }
    
    public AbstractionNetwork<BASENODE_T> getBaseAbstractionNetwork() {
        return baseAbstractionNetwork;
    }
    
    /**
     * Returns the partition node that contains the given singly rooted node,
     * if such a partition node exists in the given partitioned abstraction network.
     * 
     * @param node
     * @return 
     */
    public BASENODE_T getPartitionNodeFor(NODE_T node) {
        
        Optional<BASENODE_T> containerNode = baseAbstractionNetwork.getNodes().stream().filter((baseNode) -> {
            return baseNode.getInternalNodes().contains(node);
        }).findAny();

        return containerNode.get();
    }
    
    public Set<Concept> getOverlappingConcepts() {
        
        Set<Concept> overlappingConcepts = new HashSet<>(); 
        
        this.baseAbstractionNetwork.getNodes().forEach( (node) -> {
            overlappingConcepts.addAll(node.getOverlappingConcepts());
        });
        
        return overlappingConcepts;
    }
    
    public Set<NODE_T> getOverlappingNodes() {
        Set<NODE_T> overlappingNodes = new HashSet<>();
        
        this.baseAbstractionNetwork.getNodes().forEach( (node) -> {
            node.getOverlappingConceptDetails().forEach( (details) -> {
                OverlappingConceptDetails<NODE_T> d = (OverlappingConceptDetails<NODE_T>)details;
                
                overlappingNodes.addAll(d.getNodes());
            });
        });
        
        return overlappingNodes;
    }
}
