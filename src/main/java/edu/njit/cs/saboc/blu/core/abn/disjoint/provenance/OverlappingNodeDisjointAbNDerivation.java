package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a disjoint abstraction network based 
 * on the selection of an overlapping singly rooted node.
 * 
 * @author Chris O
 * @param <T>
 */
public class OverlappingNodeDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final T overlappingNode;
    
    public OverlappingNodeDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            T overlappingNode) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.overlappingNode = overlappingNode;
    }

    public T getOverlappingNode() {
        return overlappingNode;
    }
    
    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        return disjointAbN.getOverlappingNodeDisjointAbN(overlappingNode);
    }

    @Override
    public String getDescription() {
        return String.format("%s (overlapping node)", sourceDisjointAbNDerivation.getDescription());
    }

    @Override
    public String getName() {
        return String.format("%s %s", overlappingNode.getName(), sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Overlapping Node %s", sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "OverlappingNodeDisjointAbNDerivation");
        result.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.put("OverlappingNodeRootId", overlappingNode.getRoot().getIDAsString());
        
        return result;       
    }
}
