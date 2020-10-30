package edu.njit.cs.saboc.blu.core.abn.targetbased.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkGenerator;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Optional;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class TargetAbNFromPAreaDerivation extends TargetAbNDerivation {
    
    private final PAreaTaxonomyDerivation sourceDerivation;
    
    public TargetAbNFromPAreaDerivation(
            TargetAbNDerivation derivation,
            PAreaTaxonomyDerivation sourceDerivation) {
        
        super(derivation);
        
        this.sourceDerivation = sourceDerivation;
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "TargetAbNFromPAreaDerivation");
        result.put("PAreaTaxonomyDerivation", sourceDerivation.serializeToJSON());   
        result.put("TargetAbNDerivation", super.serializeToJSON());

        return result;
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Target Abstraction Network from Partial-area";
    }

    @Override
    public String getName() {
        return String.format("%s %s", super.getSourceHierarchyRoot().getName(), this.getAbstractionNetworkTypeName());
    }

    @Override
    public TargetAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy<PArea> taxonomy = sourceDerivation.getAbstractionNetwork(ontology);

        Optional<PArea> optPArea = taxonomy.getNodes().stream().filter((parea) -> {
            return parea.getRoot().equals(super.getSourceHierarchyRoot());
        }).findAny();

        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();

        TargetAbstractionNetwork<TargetGroup> targetAbN = generator.deriveTargetAbNFromPArea(
                super.getFactory(), 
                taxonomy, 
                optPArea.get(), 
                super.getPropertyType(), 
                ontology.getConceptHierarchy().getSubhierarchyRootedAt(
                        super.getTargetHierarchyRoot()));
        
        return targetAbN;
    }

    @Override
    public String getDescription() {
        
        return String.format("Created a Target Abstraction Network "
                + "from the %s partial-area in the %s.", 
                super.getSourceHierarchyRoot().getName(), 
                this.sourceDerivation.getName());
    }
}
