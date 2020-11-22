
package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate cluster TAN
 * 
 * @author Chris O
 */
public class AggregateTANDerivation extends ClusterTANDerivation 
        implements AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation nonAggregateSourceDerivation;
    private final AggregatedProperty ap;    
    
    public AggregateTANDerivation(ClusterTANDerivation nonAggregateSourceDerivation, AggregatedProperty aggregatedProperty) {
        super(nonAggregateSourceDerivation);
        
        this.nonAggregateSourceDerivation = nonAggregateSourceDerivation;
        this.ap = aggregatedProperty;
    }
    
    public AggregateTANDerivation(AggregateTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getNonAggregateSourceDerivation(), deriveTaxonomy.getAggregatedProperty());
    }
    
    @Override
    public ClusterTANDerivation getNonAggregateSourceDerivation() {
        return nonAggregateSourceDerivation;
    }
    
    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public String getDescription() {
        if (ap.getAutoScaled()) {
            return String.format("%s (weighted aggregated: %d)", nonAggregateSourceDerivation.getDescription(), ap.getAutoScaleBound());
        }
        else if (ap.getWeighted()) {
            return String.format("%s (weighted aggregated: %d)", nonAggregateSourceDerivation.getDescription(), ap.getBound());
        }
        return String.format("%s (aggregated: %d)", nonAggregateSourceDerivation.getDescription(), ap.getBound());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        return getNonAggregateSourceDerivation().getAbstractionNetwork(ontology).getAggregated(ap);
    }
    
    @Override
    public String getName() {
        if (ap.getAutoScaled()) {
            return String.format("%s (Auto Weighted Aggregated)", nonAggregateSourceDerivation.getName());
        }
        else if (ap.getWeighted()) {
            return String.format("%s (Weighted Aggregated)", nonAggregateSourceDerivation.getName()); 
        }
        else
            return String.format("%s (Aggregated)", nonAggregateSourceDerivation.getName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Tribal Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "AggregateTANDerivation");       
        result.put("BaseDerivation", nonAggregateSourceDerivation.serializeToJSON());   
        result.put("Bound", ap.getBound());
        result.put("isWeightedAggregated", ap.getWeighted());
        result.put("AutoScaleBound", ap.getAutoScaleBound());
        result.put("isAutoScaled", ap.getAutoScaled());
        
        return result;
    }

    @Override
    public boolean isWeightedAggregated() {
        return ap.getWeighted();
    }

    @Override
    public AggregatedProperty getAggregatedProperty() {
        return ap;
    }
}
