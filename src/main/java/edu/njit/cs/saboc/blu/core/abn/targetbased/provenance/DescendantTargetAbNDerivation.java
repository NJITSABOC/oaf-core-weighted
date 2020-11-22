package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris Ochs
 */
public class DescendantTargetAbNDerivation extends TargetAbNDerivation 
    implements RootedSubAbNDerivation<TargetAbNDerivation> {
    
    private final TargetAbNDerivation base;
    private final Concept rootConcept;
    
    public DescendantTargetAbNDerivation(TargetAbNDerivation base, Concept rootConcept) {
        super(base);
        
        this.base = base;
        this.rootConcept = rootConcept;
    }
    
    public DescendantTargetAbNDerivation(DescendantTargetAbNDerivation deriveAbN) {
        this(deriveAbN.getSuperAbNDerivation(), deriveAbN.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return rootConcept;
    }

    @Override
    public TargetAbNDerivation getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived Descendant Target Abstraction Network (Target Group: %s)", rootConcept.getName());
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        TargetAbstractionNetwork targetAbN = base.getAbstractionNetwork(ontology);
        
        Set<TargetGroup> group = targetAbN.getTargetGroupsWith(rootConcept);
        
        return targetAbN.createDescendantTargetAbN(group.iterator().next());
    }
    
    @Override
    public String getName() {
        return String.format("%s %s", rootConcept.getName(), getAbstractionNetworkTypeName()); 
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Descendant Target Abstraction Network";
    }

    @Override
    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();

        result.put("ClassName", "DescendantTargetAbNDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", rootConcept.getIDAsString());   
        
        return result;
    }  
}