package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate target abstraction network
 * 
 * @author Chris O
 */
public class AggregateTargetAbNDerivation extends TargetAbNDerivation 
    implements AggregateAbNDerivation<TargetAbNDerivation> {
    
    private final TargetAbNDerivation nonAggregateSource;
    private final AggregatedProperty ap;
    
    public AggregateTargetAbNDerivation(
            TargetAbNDerivation nonAggregateSource, 
            AggregatedProperty aggregatedProperty) {
        
        super(nonAggregateSource);
        
        this.nonAggregateSource = nonAggregateSource;
        this.ap = aggregatedProperty;
    }

    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public TargetAbNDerivation getNonAggregateSourceDerivation() {
        return nonAggregateSource;
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        TargetAbstractionNetwork targetAbN = this.getNonAggregateSourceDerivation().getAbstractionNetwork(ontology);
        
        return targetAbN.getAggregated(ap);
    }

    @Override
    public String getDescription() {      
        if (ap.getAutoScaled()) {
            return String.format("%s (auto weighted aggregated: %d)", super.getDescription(), ap.getAutoScaleBound());
        }
        if (ap.getWeighted()) {
            return String.format("%s (weighted aggregated: %d)", super.getDescription(), ap.getBound());

        }
        return String.format("%s (aggregated: %d)", super.getDescription(), ap.getBound());
    }

    @Override
    public String getName() {
        if (ap.getAutoScaled()) {
            return String.format("%s (Auto Weighted Aggregated)", super.getName());
        }
        else if (ap.getWeighted()) {
            return String.format("%s (Weighted Aggregated)", super.getName());
        } 
        else
            return String.format("%s (Aggregated)", super.getName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName","AggregateTargetAbNDerivation");       
        result.put("BaseDerivation", nonAggregateSource.serializeToJSON());   
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
