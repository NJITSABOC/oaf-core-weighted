
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an ancestor sub TAN
 * 
 * @author Chris O
 */
public class AncestorSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation base;
    private final Concept clusterRootConcept;
    
    public AncestorSubTANDerivation(ClusterTANDerivation base, Concept clusterRootConcept) {
        super(base);
        
        this.base = base;
        this.clusterRootConcept = clusterRootConcept;
    }
    
    public AncestorSubTANDerivation(AncestorSubTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedRoot());
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
        return String.format("Derived ancestor Sub TAN (Cluster: %s)", clusterRootConcept.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        ClusterTribalAbstractionNetwork tan = base.getAbstractionNetwork(ontology);
        
        Set<Cluster> clusters = tan.getNodesWith(clusterRootConcept);
        
        return tan.createAncestorTAN(clusters.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", clusterRootConcept.getName(), getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Ancestor Sub Tribal Abstraction Network";
    }

    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();

        result.put("ClassName", "AncestorSubTANDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", clusterRootConcept.getIDAsString());   
        
        return result;
    }  
}
