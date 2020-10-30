
package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create an ancestor subtaxonomy
 * 
 * @author Chris O
 */
public class AncestorSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
    implements RootedSubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation base;
    private final Concept pareaRootConcept;
    
    public AncestorSubtaxonomyDerivation(PAreaTaxonomyDerivation base, Concept pareaRootConcept) {
        super(base);
        
        this.base = base;
        this.pareaRootConcept = pareaRootConcept;
    }
    
    public AncestorSubtaxonomyDerivation(AncestorSubtaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedRoot());
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
        return String.format("Derived ancestor subtaxonomy (PArea: %s)", pareaRootConcept.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy taxonomy = base.getAbstractionNetwork(ontology);
        
        Set<PArea> pareas = taxonomy.getNodesWith(pareaRootConcept);
        
        return taxonomy.createAncestorSubtaxonomy(pareas.iterator().next());
    }

    @Override
    public String getName() {
        return String.format("%s %s", 
                pareaRootConcept.getName(), 
                getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Ancestor Subtaxonomy";
    }
        
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "AncestorSubtaxonomyDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   
        result.put("ConceptID", pareaRootConcept.getIDAsString());
        
        return result;
    }
}
