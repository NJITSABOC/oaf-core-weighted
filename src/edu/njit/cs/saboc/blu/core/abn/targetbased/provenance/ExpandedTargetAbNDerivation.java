package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class ExpandedTargetAbNDerivation extends TargetAbNDerivation 
        implements SubAbNDerivation<TargetAbNDerivation> {
    
    private final Concept aggregateTargetGroupRoot;
    private final TargetAbNDerivation base;
    
    public ExpandedTargetAbNDerivation(
            TargetAbNDerivation base, 
            Concept aggregateTargetGroupRoot) {
        super(base);
        
        this.base = base;
        this.aggregateTargetGroupRoot = aggregateTargetGroupRoot;
    }
    
    public ExpandedTargetAbNDerivation(ExpandedTargetAbNDerivation derivedTargetAbN) {
        this(derivedTargetAbN.getSuperAbNDerivation(), 
                derivedTargetAbN.getExpandedAggregatePAreaRoot());
    }
    
    public Concept getExpandedAggregatePAreaRoot() {
        return aggregateTargetGroupRoot;
    }

    @Override
    public TargetAbNDerivation getSuperAbNDerivation() {
        return base;
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        
        TargetAbstractionNetwork baseAggregated = base.getAbstractionNetwork(ontology);
        
        AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork> aggregateTargetAbN = 
                (AggregateAbstractionNetwork<AggregateTargetGroup, TargetAbstractionNetwork>)baseAggregated;
        
        Set<AggregateTargetGroup> pareas = baseAggregated.getNodesWith(aggregateTargetGroupRoot);
        
        return aggregateTargetAbN.expandAggregateNode(pareas.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded aggregate target abstraction network (%s)", 
                aggregateTargetGroupRoot.getName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "ExpandedTargetAbNDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", aggregateTargetGroupRoot.getIDAsString());
               
        return result;
    }
}
