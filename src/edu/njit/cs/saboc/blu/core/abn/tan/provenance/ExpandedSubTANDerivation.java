package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateCluster;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an expanded Sub TAN
 * 
 * @author Chris O
 */
public class ExpandedSubTANDerivation extends ClusterTANDerivation 
        implements SubAbNDerivation<ClusterTANDerivation> {
    
    private final Concept aggregateClusterRoot;
    private final ClusterTANDerivation base;
    
    public ExpandedSubTANDerivation(
            ClusterTANDerivation base, 
            Concept aggregateClusterRoot) {
        
        super(base);
        
        this.base = base;
        this.aggregateClusterRoot = aggregateClusterRoot;
    }
    
    public ExpandedSubTANDerivation(ExpandedSubTANDerivation derivedTaxonomy) {
        this(derivedTaxonomy.getSuperAbNDerivation(), derivedTaxonomy.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregateClusterRoot;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return base;
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        ClusterTribalAbstractionNetwork baseAggregated = base.getAbstractionNetwork(ontology);
        
        AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork> aggregateTAN = 
                (AggregateAbstractionNetwork<AggregateCluster, ClusterTribalAbstractionNetwork>)baseAggregated;
        
        Set<AggregateCluster> pareas = baseAggregated.getNodesWith(aggregateClusterRoot);
        
        return aggregateTAN.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate cluster (%s)", aggregateClusterRoot.getName());
    }


    @Override
    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();

        result.put("ClassName","ExpandedSubTANDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", aggregateClusterRoot.getIDAsString());   
        
        return result;
    }   
     
    @Override
    public String getName() {
        return String.format("%s %s", aggregateClusterRoot.getName(), getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Expanded Tribal Abstraction Network";
    }
}
