package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a Root Sub TAN
 * 
 * @author Chris O
 */
public class RootSubTANDerivation extends ClusterTANDerivation 
        implements RootedSubAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation base;
    private final Concept clusterRootConcept;
    
    public RootSubTANDerivation(ClusterTANDerivation base, Concept clusterRootConcept) {
        super(base);
        
        this.base = base;
        this.clusterRootConcept = clusterRootConcept;
    }
    
    public RootSubTANDerivation(RootSubTANDerivation base) {
        this(base.getSuperAbNDerivation(), base.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return clusterRootConcept;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived Root Sub TAN (Cluster: %s)", clusterRootConcept.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        ClusterTribalAbstractionNetwork tan = base.getAbstractionNetwork(ontology);
        
        Set<Cluster> clusters = tan.getNodesWith(clusterRootConcept);
        
        return tan.createRootSubTAN(clusters.iterator().next());
    }

    @Override
    public String getName() {
        return String.format("%s %s", clusterRootConcept.getName(), getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Descendant Sub Tribal Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "RootSubTANDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", clusterRootConcept.getIDAsString());   
        
        return result;
    }   
}
