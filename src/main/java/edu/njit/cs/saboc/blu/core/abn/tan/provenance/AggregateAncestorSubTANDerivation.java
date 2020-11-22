package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an aggregate ancestor sub TAN
 * 
 * @author Chris O
 */
public class AggregateAncestorSubTANDerivation extends ClusterTANDerivation 
    implements RootedSubAbNDerivation<ClusterTANDerivation>, AggregateAbNDerivation<ClusterTANDerivation> {
    
    private final ClusterTANDerivation aggregateBase;
    private final Concept selectedAggregateClusterRoot;
    private final AggregatedProperty ap;
    
    public AggregateAncestorSubTANDerivation(
            ClusterTANDerivation aggregateBase, 
            AggregatedProperty aggregatedProperty,
            Concept selectedAggregateClusterRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.ap = aggregatedProperty;
        this.selectedAggregateClusterRoot = selectedAggregateClusterRoot;
    }
    
    public AggregateAncestorSubTANDerivation(AggregateAncestorSubTANDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getAggregatedProperty(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregateClusterRoot;
    }

    @Override
    public ClusterTANDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public ClusterTANDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<ClusterTANDerivation> derivedAggregate = (AggregateAbNDerivation<ClusterTANDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        if (ap.getWeighted()) {
            return String.format("Derived weighted aggregate ancestor TAN (Cluster: %s)", selectedAggregateClusterRoot.getName());
        }
        return String.format("Derived aggregate ancestor TAN (Cluster: %s)", selectedAggregateClusterRoot.getName());
    }

    @Override
    public ClusterTribalAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        ClusterTribalAbstractionNetwork sourceTAN = this.getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        Set<Cluster> clusters = sourceTAN.getNodesWith(selectedAggregateClusterRoot);
        
        return sourceTAN.createAncestorTAN(clusters.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", 
                selectedAggregateClusterRoot.getName(), 
                getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Ancestor Sub TAN";
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AggregateAncestorSubTANDerivation");       
        result.put("BaseDerivation", aggregateBase.serializeToJSON());   
        result.put("Bound", ap.getBound());
        result.put("ConceptID", selectedAggregateClusterRoot.getIDAsString());
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
