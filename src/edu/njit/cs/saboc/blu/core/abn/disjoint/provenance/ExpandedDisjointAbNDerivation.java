package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an expanded disjoint abstraction network
 * 
 * @author Chris O
 */
public class ExpandedDisjointAbNDerivation extends DisjointAbNDerivation
        implements SubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Concept expandedAggregateNodeRoot;
    
    public ExpandedDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Concept expandedAggregateNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.expandedAggregateNodeRoot = expandedAggregateNodeRoot;
    }

    public Concept getExpandedAggregateNodeRoot() {
        return expandedAggregateNodeRoot;
    }
    
    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        DisjointAbstractionNetwork disjointAbN = getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
         AggregateAbstractionNetwork<AggregateDisjointNode, DisjointAbstractionNetwork> aggregateDisjointAbN = 
                (AggregateAbstractionNetwork<AggregateDisjointNode, DisjointAbstractionNetwork>)disjointAbN;
        
        Set<DisjointNode> nodes = disjointAbN.getNodesWith(expandedAggregateNodeRoot);
        
        return aggregateDisjointAbN.expandAggregateNode((AggregateDisjointNode)nodes.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("Expanded %s", expandedAggregateNodeRoot.getName());
    }

    @Override
    public String getName() {
        return String.format("%s %s", expandedAggregateNodeRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Expanded %s", sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "ExpandedDisjointAbNDerivation");       
        result.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.put("ConceptID", expandedAggregateNodeRoot.getIDAsString());

        return result;       
    }           
}
