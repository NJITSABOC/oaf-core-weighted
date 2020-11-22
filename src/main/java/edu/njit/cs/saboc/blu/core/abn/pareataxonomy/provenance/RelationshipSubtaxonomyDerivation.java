package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.SubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Stores the arguments for creating a relationship subtaxonomy
 * 
 * @author Chris O
 */
public class RelationshipSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
        implements SubAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final Set<InheritableProperty> selectedProperties;
    private final PAreaTaxonomyDerivation base;
    
    public RelationshipSubtaxonomyDerivation(
            PAreaTaxonomyDerivation base, 
            Set<InheritableProperty> selectedProperties) {
        
        super(base);
        
        this.base = base;
        this.selectedProperties = selectedProperties;
    }
    
    public RelationshipSubtaxonomyDerivation(RelationshipSubtaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), deriveTaxonomy.getSelectedProperties());
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
         return base;
    }
    
    public Set<InheritableProperty> getSelectedProperties() {
        return selectedProperties;
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        return base.getAbstractionNetwork(ontology).getRelationshipSubtaxonomy(selectedProperties);
    }

    @Override
    public String getDescription() {
        return "Created relationship subtaxonomy";
    }

    @Override
    public String getName() {
        if(base instanceof SimplePAreaTaxonomyDerivation) {
            String rootName = ((SimplePAreaTaxonomyDerivation)base).getRoot().getName();
            
            return String.format("%s %s", rootName, getAbstractionNetworkTypeName());
        }
        
        return "[Unknown Relationship Subtaxonomy Type]";
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Relationship Subtaxonomy";
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName", "RelationshipSubtaxonomyDerivation");       
        result.put("BaseDerivation", base.serializeToJSON());   

        JSONArray propertyids = new JSONArray();
        
        selectedProperties.forEach(sp -> {
            propertyids.add(sp.getIDAsString());
        });
        
        result.put("PropertyIDs", propertyids);
        
        return result;
    }
}
