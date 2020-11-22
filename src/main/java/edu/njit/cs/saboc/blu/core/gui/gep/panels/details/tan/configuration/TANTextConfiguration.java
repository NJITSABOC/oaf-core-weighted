package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.AggregateAbNTextGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class TANTextConfiguration extends PartitionedAbNTextConfiguration<Cluster, Band> {

    private final ClusterTribalAbstractionNetwork<Cluster> tan;
    
    private final BandTANTextConfiguration bandTANTextConfig;
    
    public TANTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            ClusterTribalAbstractionNetwork<Cluster> tan) {
        
        super(ontologyEntityNameConfig);
        
        this.tan = tan;
        
        this.bandTANTextConfig = new BandTANTextConfiguration(ontologyEntityNameConfig);
    }
    
    @Override
    public BandTANTextConfiguration getBaseAbNTextConfiguration() {
        return bandTANTextConfig;
    }
    
    public ClusterTribalAbstractionNetwork getTAN() {
        return tan;
    }

    @Override
    public String getAbNName() {
        return tan.getDerivation().getName();
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Cluster TANs";
        } else {
            return "Cluster TAN";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if (plural) {
            return "Clusters";
        } else {
            return "Cluster";
        }
    }

    @Override
    public String getDisjointNodeTypeName(boolean plural) {
        if (plural) {
            return "Disjoint Clusters";
        } else {
            return "Disjoint Cluster";
        }
    }

    @Override
    public String getNodeHelpDescription(Cluster node) {
        
        String result = "A <b>cluster</b> summarizes a subhierarchy of "
                + "<conceptTypeName count=2> root at a specific point of intersection "
                + "between two or more subhierarchies (the \"root\").";

        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(result);
    }

    @Override
    public String getAbNSummary() {
  
        Set<Concept> intersectionConcepts = new HashSet<>();
        
        HashMap<Concept, Set<Cluster>> intersectingPatriarchClusters = new HashMap<>();
        
        tan.getClusters().forEach((cluster) -> {

            Set<Concept> patriarchs = cluster.getPatriarchs();
            
            if (patriarchs.size() > 1) {
                intersectionConcepts.addAll(cluster.getConcepts());
                
                patriarchs.forEach( (p) -> {
                    if(!intersectingPatriarchClusters.containsKey(p)) {
                        intersectingPatriarchClusters.put(p, new HashSet<>());
                    }
                    
                    intersectingPatriarchClusters.get(p).add(cluster);
                });
            }
        });
        
        String result = "<html>The <b><tanName></b> summarizes "
                + "<font color = 'RED'><conceptCount></font> <conceptTypeName count=<conceptCount>> "
                + "in <font color = 'RED'><bandCount></font> band(s) and <font color='RED'><clusterCount></font> cluster(s).<p>"
                
                + "There are a total of <font color='RED'><patriarchCount></font> patriarch clusters that intersect. "
                + "A total of <font color = 'RED'><intersectingConceptCount></font> "
                + "<conceptTypeName count=<intersectingConceptCount>> "
                + "are descendants of more than one patriarch.";

        result = result.replaceAll("<tanName>", tan.getDerivation().getName());
        result = result.replaceAll("<conceptCount>", Integer.toString(tan.getSourceHierarchy().size()));
        result = result.replaceAll("<bandCount>", Integer.toString(tan.getBands().size()));
        result = result.replaceAll("<clusterCount>", Integer.toString(tan.getClusters().size()));
        result = result.replaceAll("<patriarchCount>", Integer.toString(tan.getPatriarchClusters().size()));
        result = result.replaceAll("<intersectingConceptCount>", Integer.toString(intersectionConcepts.size()));
        
        
        if (tan.isAggregated()) {
            AggregateAbNTextGenerator generator = new AggregateAbNTextGenerator();

            result += "<p>";

            result += generator.generateAggregateDetailsText(this, tan);
        }
        
        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(result);
    }

    @Override
    public String getAbNHelpDescription() {
        
        String result = "A <b>Tribal Abstraction Network (TAN)</b> is an abstraction network that summarizes the major points of intersection within "
                + "a hierarchy of <conceptTypeName count=2>. Given a hierarchy of <conceptTypeName count=2>, the children of the root of the hierarchy "
                + "are defined as <i>patriarchs</i>. "
                
                + "Patriarchs are root <conceptTypeName count=2> of subhierarchies within the overall hierarchy. "
                + "The subhierarchies rooted at the patriarchs may intersect and a <conceptTypeName> in the hierarchy may be a descendant of multiple patriarchs."
                + "<p>"
                + "The TAN is composed of two kinds of nodes that summarize sets of such <conceptTypeName count=2>: Bands and Clusters.<p>"
                + "A <b>band</b> summarizes the set of all <conceptTypeName count=2> that are descendants of the exact same patriarchs. "
                + "Bands are organized into color coded levels according to the number of patriarchs their <conceptTypeName count=2> are descendnats "
                + "of (e.g., green represents descendents of two patriarchs). Bands are labeled with this set of patriarchs, the total number of "
                + "<conceptTypeName count=2> that are summarized by the band, and the total number of <i>clusters</i> in the band."
                + "<p>"
                + "A <b>cluster</b> summarizes a subhierarchy of <conceptTypeName count=2> that exists at one intersection point in a band. "
                + "Clusters are shown as white boxes within each band. Clusters are named after the root <conceptTypeName> that "
                + "is at the exact point of intersection. "
                + "The total number of <conceptTypeName count=2> summarized by a cluster is shown in parenthesis."
                + "<p>"
                + "A TAN also summarizes the hierarchical connections between <conceptTypeName count=2>. "
                + "Clicking on a cluster will show you the parent clusters (in blue) "
                + "and child clusters (in purple) that summarize descendants that are descendants of one or more  patriarchs.";

        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(result);
    }
}
