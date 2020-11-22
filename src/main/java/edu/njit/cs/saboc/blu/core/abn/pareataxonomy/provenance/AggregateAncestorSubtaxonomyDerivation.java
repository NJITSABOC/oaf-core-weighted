package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.provenance.AggregateAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.provenance.RootedSubAbNDerivation;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import java.util.Set;
import org.json.simple.JSONObject;

/**
 * Stores the arguments for creating aggregate an ancestor subtaxonomy
 * 
 * @author Chris O
 */
public class AggregateAncestorSubtaxonomyDerivation extends PAreaTaxonomyDerivation 
    implements RootedSubAbNDerivation<PAreaTaxonomyDerivation>, AggregateAbNDerivation<PAreaTaxonomyDerivation> {
    
    private final PAreaTaxonomyDerivation aggregateBase;
    private final AggregatedProperty ap;
    private final Concept selectedAggregatePAreaRoot;
    
    public AggregateAncestorSubtaxonomyDerivation(
            PAreaTaxonomyDerivation aggregateBase, 
            AggregatedProperty aggregatedProperty,
            Concept selectedAggregatePAreaRoot) {
        
        super(aggregateBase);
        
        this.aggregateBase = aggregateBase;
        this.ap = aggregatedProperty;
        this.selectedAggregatePAreaRoot = selectedAggregatePAreaRoot;        
    }
    
    public AggregateAncestorSubtaxonomyDerivation(AggregateAncestorSubtaxonomyDerivation deriveTaxonomy) {
        this(deriveTaxonomy.getSuperAbNDerivation(), 
                deriveTaxonomy.getAggregatedProperty(),
                deriveTaxonomy.getSelectedRoot());
    }

    @Override
    public Concept getSelectedRoot() {
        return selectedAggregatePAreaRoot;
    }

    @Override
    public PAreaTaxonomyDerivation getSuperAbNDerivation() {
        return aggregateBase;
    }

    @Override
    public int getBound() {
        return ap.getBound();
    }

    @Override
    public PAreaTaxonomyDerivation getNonAggregateSourceDerivation() {
        AggregateAbNDerivation<PAreaTaxonomyDerivation> derivedAggregate = 
                (AggregateAbNDerivation<PAreaTaxonomyDerivation>)this.getSuperAbNDerivation();
        
        return derivedAggregate.getNonAggregateSourceDerivation();
    }
  
    @Override
    public String getDescription() {
        if (ap.getWeighted()) {
            return String.format("Derived weighted aggregate ancestors subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
        }
        return String.format("Derived aggregate ancestors subtaxonomy (PArea: %s)", selectedAggregatePAreaRoot.getName());
    }

    @Override
    public PAreaTaxonomy getAbstractionNetwork(Ontology<Concept> ontology) {
        PAreaTaxonomy sourceTaxonomy = this.getSuperAbNDerivation().getAbstractionNetwork(ontology);
        
        Set<PArea> pareas = sourceTaxonomy.getNodesWith(selectedAggregatePAreaRoot);
        
        return sourceTaxonomy.createAncestorSubtaxonomy(pareas.iterator().next());
    }

    @Override
    public String getName() {
        return String.format("%s %s", selectedAggregatePAreaRoot.getName(), getAbstractionNetworkTypeName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return "Aggregate Ancestor Subtaxonomy";
    }
    
    @Override
    public JSONObject serializeToJSON() {
        JSONObject result = new JSONObject();

        result.put("ClassName","AggregateAncestorSubtaxonomyDerivation");
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
        return  ap;
    }
}
