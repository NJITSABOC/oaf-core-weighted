
package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a subset disjoint abstraction network
 * 
 * @author Chris O
 * @param <T>
 */
public class SubsetDisjointAbNDerivation<T extends SinglyRootedNode> extends DisjointAbNDerivation
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Set<T> subset;
    
    public SubsetDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Set<T> subset) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.subset = subset;
    }

    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    public Set<T> getSubset() {
        return subset;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        return getSuperAbNDerivation().getAbstractionNetwork(ontology).getSubsetDisjointAbN(subset);
    }

    @Override
    public String getDescription() {
        return String.format("%s (subset)", sourceDisjointAbNDerivation.getDescription());
    }

    @Override
    public String getName() {
        return String.format("%s (Subset)", sourceDisjointAbNDerivation.getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Subset %s", sourceDisjointAbNDerivation.getName());
    }

    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "SubsetDisjointAbNDerivation");       
        result.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   

        JSONArray arr = new JSONArray();
        
        subset.forEach(node ->{
            arr.add(node.getRoot().getIDAsString());
        });        
        
        result.put("RootNodeIDs", arr);
        
        return result;
    }
}
