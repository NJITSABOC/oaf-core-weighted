package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.AggregateAbNTextGenerator;

/**
 *
 * @author Chris O
 */
public abstract class PAreaTaxonomyTextConfiguration extends PartitionedAbNTextConfiguration<PArea, Area> {
    
    private final AreaTaxonomyTextConfiguration areaTaxonomyTextConfig;
    
    private final PAreaTaxonomy taxonomy;

    public PAreaTaxonomyTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            PAreaTaxonomy taxonomy) {
        
        super(ontologyEntityNameConfig);
        
        this.taxonomy = taxonomy;
        
        this.areaTaxonomyTextConfig = new AreaTaxonomyTextConfiguration(
                this.getOntologyEntityNameConfiguration());
    }
    
    public PAreaTaxonomy getPAreaTaxonomy() {
        return taxonomy;
    }

    @Override
    public AreaTaxonomyTextConfiguration getBaseAbNTextConfiguration() {
        return areaTaxonomyTextConfig;
    }

    @Override
    public String getAbNName() {
        return taxonomy.getDerivation().getName();
    }

    @Override
    public String getAbNSummary() {
        String result = "<html>The <b><taxonomyName></b> summarizes "
                + "<font color = 'RED'><conceptCount></font>"
                + " <conceptTypeName count=<conceptCount>> in "
                + "<font color = 'RED'><areaCount></font> area(s) and "
                + "<font color = 'RED'><pareaCount></font> partial-areas(s).";
        
        result = result.replaceAll("<taxonomyName>", taxonomy.getDerivation().getName());
        result = result.replaceAll("<conceptCount>", Integer.toString(taxonomy.getSourceHierarchy().getNodes().size()));
        result = result.replaceAll("<areaCount>", Integer.toString(taxonomy.getAreas().size()));
        result = result.replaceAll("<pareaCount>", Integer.toString(taxonomy.getNodeCount()));
        

        if (taxonomy.isAggregated()) {
            AggregateAbNTextGenerator generator = new AggregateAbNTextGenerator();

            result += "<p>";

            result += generator.generateAggregateDetailsText(this, taxonomy);
        }

        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(result);
    }
    
    @Override
    public String getAbNHelpDescription() {
        String pareaTaxonomyDesc = ""
                + "A partial-area taxonomy summarizes structurally similar "
                + "and semantically similar <conceptTypeName count=2>. The partial-area taxonomy highlights "
                + "high-level structural and hierarchical aggregation patterns in an ontology. "
                + "<p>"
                + "A partial-area taxonomy is composed of two kinds of nodes that summarize sets of <conceptTypeName count=2>. <p>"
                + "<b>Areas</b> summarize disjoint sets of <conceptTypeName count=2> that are modeled using "
                + "the exact same types of <propertyTypeName count=2>. "
                + "The <conceptTypeName count=2> summarized by an area are all <i>structurally similar</i> since they have the same types of"
                + "<propertyTypeName count=2>. "
                + "Areas are shown as colored boxes and organized into color-coded levels according to their number of <propertyTypeName count=2> "
                + "types. "
                + "Each area is labeled with its set of <propertyTypeName count=2>, the number of <conceptTypeName count=2> it summarizes, "
                + "and the number of <i>partial-areas</i>."
                + "<p>"
                + "A <b>partial-area</b> summarizes subhierarchies of <i>semantically similar</i> <conceptTypeName count=2> in an area. "
                + "There may be multiple such subhierarchies and thus multiple partial-areas. Partial-areas are shown as white boxes "
                + "within each area. Each partial-area is labaled with the <conceptTypeName> that is the root of the subhierarchy "
                + "and the total number of <conceptTypeName count=2> summarized by the partial-area (in parenthesis). "
                + "<p>"
                + "The hierarchical connections between <conceptTypeName count=2> are also summarized by a partial-area taxonomy. "
                + "When a given partial-area is selected  its parent partial-areas, which have fewer types of"
                + "<propertyTypeName count=2>, are highlighted (in blue) and its child partial-areas, which have more types of "
                + "<propertyTypeName count=2>, are highlighted (in purple).";

        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(pareaTaxonomyDesc);
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Partial-area Taxonomies";
        } else {
            return "Partial-area Taxonomy";
        }
    }
    
    @Override
    public String getDisjointNodeTypeName(boolean plural) {
        if(plural) {
            return "Disjoint partial-areas";
        } else {
            return "Disjoint partial-area";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Partial-areas";
        } else {
            return "Partial-area";
        }
    }
    
    @Override
    public String getNodeHelpDescription(PArea parea) {

        String helpDesc = "<html>A <b>partial-area</b> summarizes a subhierarchy of "
                + "structurally and semantically similar <conceptTypeName count=2> in an ontology. "
                    + "All of the <conceptTypeName count=2> in a partial-area are defined using the exact same "
                    + "types of <propertyTypeName count=2>. "
                    + "All of the <conceptTypeName count=2> summarized by a partial-area are "
                    + "descendants of the same <conceptTypeName>, called the <i>root</i>, "
                    + "which is the introduction point for the set of <propertyTypeName count=2>. "
                    + "A partial-area is named after its root and it is labeled with the total number "
                    + "of <conceptTypeName count=2> summarized by the partial-area (in parenthesis).";
        
        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(helpDesc);
    }
}
