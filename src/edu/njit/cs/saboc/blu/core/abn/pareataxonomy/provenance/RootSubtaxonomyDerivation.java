package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a root subtaxonomy
 * 
 * @author Chris O
 */
public class RootSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements RootedSubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation base;
    private final Concept pareaRootConcept;
    
    public RootSubtaxonomyDerivation(PAreaTaxonomyDerivation base, Concept pareaRootConcept) {
        super(base);
        
        this.base = base;
        this.pareaRootConcept = pareaRootConcept;
    }
    
    public RootSubtaxonomyDerivation(RootSubtaxonomyDerivation base) {
        this(base.getSuperAbNDerivation(), base.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return pareaRootConcept;
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
        return base;
    }
    
    @Override
    public String getDescription() {
        return String.format("Derived root subtaxonomy (root: %s)", pareaRootConcept.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy taxonomy = base.getAbstractionNetwork(ontology);
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRootConcept);
        
        return taxonomy.createRootSubtaxonomy(pareas.iterator().next());
    }

    @Override
    public String getName() {
        return String.format("%s %s", pareaRootConcept.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Root Subtaxonomy";
    }

    @Override
    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();

        result.put("ClassName", "RootSubtaxonomyDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", pareaRootConcept.getIDAsString());   

        return result;
    }
}
