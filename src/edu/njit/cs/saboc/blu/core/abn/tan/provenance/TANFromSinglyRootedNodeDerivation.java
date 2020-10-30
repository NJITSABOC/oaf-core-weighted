package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.TribalAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a TAN from a singly rooted node 
 * thats in a different abstraction network
 * 
 * @author Chris O
 * 
 * @param <T>
 * @param <V>
 */
public class TANFromSinglyRootedNodeDerivation <
        T extends AbNDerivation, 
        V extends SinglyRootedNode> extends ClusterTANDerivation {
    
    private final T parentAbNDerivation;
    private final Concept nodeRoot;
    
    public TANFromSinglyRootedNodeDerivation(
            T parentAbNDerivation, 
            TANFactory factory,
            Concept nodeRoot) {
        
        super(factory);
        
        this.parentAbNDerivation = parentAbNDerivation;
        this.nodeRoot = nodeRoot;
    }
    
    public T getParentAbNDerivation() {
        return parentAbNDerivation;
    }
    
    public Concept getRootConcept() {
        return nodeRoot;
    }

    @Override
    public String getDescription() {
        return String.format("Derived TAN from %s", nodeRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        AbstractionNetwork<V> sourceAbN = parentAbNDerivation.getAbstractionNetwork(ontology);
        
        Set<V> nodes = sourceAbN.getNodesWith(nodeRoot);
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        return generator.createTANFromSinglyRootedNode(sourceAbN, nodes.iterator().next(), super.getFactory());
    }

    @Override
    public String getName() {
        return String.format("%s %s", nodeRoot.getName(), super.getAbstractionNetworkTypeName());
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName","TANFromSinglyRootedNodeDerivation");
        result.put("BaseDerivation", parentAbNDerivation.serializeToJSON());
        result.put("ConceptId", nodeRoot.getIDAsString());
        
        return result;
    }   
}
