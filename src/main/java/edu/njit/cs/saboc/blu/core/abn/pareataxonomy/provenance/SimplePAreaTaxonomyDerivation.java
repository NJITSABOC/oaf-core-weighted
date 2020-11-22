package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * Stores the arguments needed to create a regular partial-area taxonomy for an ontology
 * or a subhierarchy of an ontology 
 * 
 * @author Chris O
 */
public class SimplePAreaTaxonomyDerivation extends PAreaTaxonomyDerivation {

    private final Concept root;

    public SimplePAreaTaxonomyDerivation(
            Concept root,
            PAreaTaxonomyFactory factory) {

        super(factory);

        this.root = root;
    }
    
    public SimplePAreaTaxonomyDerivation(SimplePAreaTaxonomyDerivation derivation) {
        this(derivation.getRoot(), 
                derivation.getFactory());
    }

    public Concept getRoot() {
        return root;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomyGenerator generator = new PAreaTaxonomyGenerator();

        PAreaTaxonomy taxonomy = generator.derivePAreaTaxonomy(
                super.getFactory(),
                ontology.getConceptHierarchy().getSubhierarchyRootedAt(root));

        return taxonomy;
    }

    @Override
    public String getDescription() {
        return String.format("Derived Partial-area Taxonomy (Root: %s)", root.getName());
    }
    @Override
    public String getName() {
        return String.format("%s %s", root.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Partial-area Taxonomy";
    }

    @Override
    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();
        
        result.put("ClassName", "SimplePAreaTaxonomyDerivation");       
        result.put("ConceptID", root.getIDAsString());   
        
        return result;
    }
}
