package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class AggregateAncestorDisjointAbNDerivation extends DisjointAbNDerivation
    implements RootedSubAbNDerivation<DisjointAbNDerivation>, AggregateAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation aggregateBase;
    
    private final AggregatedProperty ap;
    private final Concept selectedAggregatePAreaRoot;
    
    public AggregateAncestorDisjointAbNDerivation(
            DisjointAbNDerivation aggregateBase, 
            AggregatedProperty aggregatedProperty,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.ap = aggregatedProperty;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;
    }
    
    public AggregateAncestorDisjointAbNDerivation(AggregateAncestorDisjointAbNDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getAggregatedProperty(), 
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public DisjointAbNDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<DisjointAbNDerivation> derivedAggregate = (AggregateAbNDerivation<DisjointAbNDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        if(ap.getWeighted())
            return String.format("Derived weighted aggregate ancestordisjoint (%s)", selectedAggregatePAreaRoot.getName());
        return String.format("Derived aggregate ancestordisjoint (%s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        DisjointAbstractionNetwork aggregateDisjointAbN = this.getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        Set<DisjointNode> nodes = aggregateDisjointAbN.getNodesWith(selectedAggregatePAreaRoot);
        
        return aggregateDisjointAbN.getAncestorDisjointAbN(nodes.iterator().next());
    }

    public String getName() {
        return String.format("%s %s", selectedAggregatePAreaRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Ancestor %s", aggregateBase.getAbstractionNetworkTypeName());
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AggregateAncestorDisjointAbNDerivation");       
        result.put("BaseDerivation", aggregateBase.serializeToJSON());
        result.put("Bound", ap.getBound());
        result.put("ConceptID", selectedAggregatePAreaRoot.getIDAsString());
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
