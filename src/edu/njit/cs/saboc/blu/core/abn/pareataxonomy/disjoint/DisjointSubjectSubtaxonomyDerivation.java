package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.disjoint;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 *
 * @author Chris O
 */
public class DisjointSubjectSubtaxonomyDerivation extends DisjointAbNDerivation {

    private final PAreaTaxonomyDerivation parentTaxonomyDerivation;
    private final Area subtaxonomyArea;

    public DisjointSubjectSubtaxonomyDerivation(
            PAreaTaxonomyDerivation parentTaxonomyDerivation,
            Area subtaxonomyArea,
            DisjointAbNFactory factory) {
        
        super(factory);
        
        this.parentTaxonomyDerivation = parentTaxonomyDerivation;
        this.subtaxonomyArea = subtaxonomyArea;
    }
    
    public PAreaTaxonomyDerivation getParentAbNDerivation() {
        return parentTaxonomyDerivation;
    }
    
    @Override
    public String getDescription() {
        return String.format("Disjointed %s (subject-based)", subtaxonomyArea.getName());
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Disjoint Subject Subtaxonomy";
    }

    @Override
    public String getName() {
        return String.format("%s %s", subtaxonomyArea.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public DisjointAbstractionNetwork getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy taxonomy = parentTaxonomyDerivation.getAbstractionNetwork(ontology);

        DisjointSubjectSubtaxonomyGenerator generator = new DisjointSubjectSubtaxonomyGenerator();
        
        return generator.createDisjointSubjectSubtaxonomy(taxonomy, subtaxonomyArea);
    }

    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "DisjointSubjectSubtaxonomy");
        result.put("BaseDerivation", parentTaxonomyDerivation.serializeToJSON());
        result.put("AreaName", this.subtaxonomyArea.getName());
        
        return result;
    }
}
