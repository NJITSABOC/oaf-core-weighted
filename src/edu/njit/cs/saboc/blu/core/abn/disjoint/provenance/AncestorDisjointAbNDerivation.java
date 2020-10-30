package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a given ancestor disjoint abstraction
 * network
 * 
 * @author Chris O
 */
public class AncestorDisjointAbNDerivation extends DisjointAbNDerivation
        implements RootedSubAbNDerivation<DisjointAbNDerivation> {
    
    private final DisjointAbNDerivation sourceDisjointAbNDerivation;
    private final Concept disjointNodeRoot;
    
    public AncestorDisjointAbNDerivation(
            DisjointAbNDerivation sourceDisjointAbNDerivation, 
            Concept disjointNodeRoot) {
        
        super(sourceDisjointAbNDerivation);
        
        this.sourceDisjointAbNDerivation = sourceDisjointAbNDerivation;
        this.disjointNodeRoot = disjointNodeRoot;
    }

    @Override
    public Concept getSelectedRoot() {
        return disjointNodeRoot;
    }

    @Override
    public DisjointAbNDerivation getSuperAbNDerivation() {
        return sourceDisjointAbNDerivation;
    }
    
    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        
        DisjointAbstractionNetwork sourceAbN = getSuperAbNDerivation().getAbstractionNetwork(ontology);
     
        Set<DisjointNode> nodes = sourceAbN.getNodesWith(disjointNodeRoot);
        
        return sourceAbN.getAncestorDisjointAbN(nodes.iterator().next());
    }

    @Override
    public String getDescription() {
        return String.format("%s (ancestor subset)", sourceDisjointAbNDerivation.getDescription());
    }

    @Override
    public String getName() {
        return String.format("%s %s", disjointNodeRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return String.format("Ancestor %s", sourceDisjointAbNDerivation.getAbstractionNetworkTypeName());
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AncestorDisjointAbNDerivation");       
        result.put("BaseDerivation", sourceDisjointAbNDerivation.serializeToJSON());   
        result.put("ConceptID", disjointNodeRoot.getIDAsString());
        
        return result;       
    }     
}
