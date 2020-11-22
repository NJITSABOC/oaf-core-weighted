package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;

import org.json.simple.JSONObject;


/**
 * Stores the arguments needed to create an aggregate disjoint abstraction 
 * network
 * 
 * @author Chris O
 */
public class AggregateDisjointAbNDerivation extends DisjointAbNDerivation
        implements AggregateAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation nonAggregateDerivation;
    private final AggregatedProperty ap;
    
    public AggregateDisjointAbNDerivation(
            DisjointAbNDerivation nonAggregateDerivation, 
            AggregatedProperty aggregatedProperty ) {
        
        super(nonAggregateDerivation);
        
        this.nonAggregateDerivation = nonAggregateDerivation;
        this.ap = aggregatedProperty;
    }

    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public DisjointAbNDerivation getNonAggregateSourceDerivation() {
        return nonAggregateDerivation;
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        return nonAggregateDerivation.getAbstractionNetwork(ontology).getAggregated(ap);
    }

    @Override
    public String getDescription() {
        if (ap.getAutoScaled()) {
            return String.format("%s (auto weighted aggregated: %d)", nonAggregateDerivation.getDescription(), ap.getAutoScaleBound());
        }
        else if (ap.getWeighted()) {
            return String.format("%s (weighted aggregate: %d)", nonAggregateDerivation.getDescription(), ap.getBound());
        }
        return String.format("%s (aggregate: %d)", nonAggregateDerivation.getDescription(), ap.getBound());
    }

    @Override
    public String getName() {
        if (ap.getAutoScaled()) {
            return String.format("%s (Auto Weighted Aggregated)", nonAggregateDerivation.getName());
        }
        else if (ap.getWeighted()) {
            return String.format("%s (Weighted Aggregated)", nonAggregateDerivation.getName());
        }
        return String.format("%s (Aggregated)", nonAggregateDerivation.getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        if (ap.getWeighted()) {
            return String.format("Weighted Aggregate %s", nonAggregateDerivation.getAbstractionNetworkTypeName());
        }
        return String.format("Aggregate %s", nonAggregateDerivation.getAbstractionNetworkTypeName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "AggregateDisjointAbNDerivation");       
        result.put("BaseDerivation", nonAggregateDerivation.serializeToJSON());   
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
