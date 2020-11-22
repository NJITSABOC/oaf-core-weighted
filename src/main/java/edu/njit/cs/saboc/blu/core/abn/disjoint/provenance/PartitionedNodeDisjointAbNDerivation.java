package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.HashSet;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class PartitionedNodeDisjointAbNDerivation<
        T extends SinglyRootedNode, 
        V extends PartitionedNode<T>> extends DisjointAbNDerivation {

    private final AbNDerivation<PartitionedAbstractionNetwork<T, V>> parentAbNDerivation;
    private final V partitionedNode;
    
    public PartitionedNodeDisjointAbNDerivation(
            DisjointAbNFactory factory,
            AbNDerivation<PartitionedAbstractionNetwork<T, V>> parentAbNDerivation,
            V partitionedNode) {
        
        super(factory);
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.partitionedNode = partitionedNode;
    }
        
    public AbNDerivation<PartitionedAbstractionNetwork<T, V>> getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    @Override
    public String getDescription() {
        return String.format("Disjointed %s", partitionedNode.getName());
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Disjoint %s", parentAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public String getName() {
        return String.format("%s %s", partitionedNode.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        
        PartitionedAbstractionNetwork<T, V> partitionedAbN = parentAbNDerivation.getAbstractionNetwork(ontology);

        Set<SinglyRootedNode> overlappingNodes = new HashSet<>();
        
        partitionedNode.getOverlappingConceptDetails().forEach( (details) -> {
            if(details.getNodes().size() > 1) {
                overlappingNodes.addAll(details.getNodes());
            }
        });
        
        DisjointAbNGenerator generator = new DisjointAbNGenerator<>();

        return generator.generateDisjointAbstractionNetwork(
                this.getFactory(),
                partitionedAbN,
                overlappingNodes);
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "PartitionedNodeDisjointAbNDerivation");
        result.put("BaseDerivation", parentAbNDerivation.serializeToJSON());
        result.put("NodeName", this.partitionedNode.getName());
        
        return result;
    }
}
