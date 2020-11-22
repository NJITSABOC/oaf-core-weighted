package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.band;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.AggregatePartitionedNodeTextGenerator;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class BandSummaryTextFactory extends NodeSummaryTextFactory<Band> {
    
    public BandSummaryTextFactory(TANConfiguration config) {
        super(config);
    }
    
    @Override
    public TANConfiguration getConfiguration() {
        return (TANConfiguration)super.getConfiguration();
    }
    
    @Override
    public String createNodeSummaryText(Band band) {
        String areaDetails = "<html><b><bandName></b> is a band that summarizes "
                + "<font color = 'RED'><conceptCount></font> <conceptTypeName count=<conceptCount>> "
                + "in <font color = 'RED'><clusterCount></font> <nodeTypeName count=<clusterCount>>.";

        if (!band.getOverlappingConcepts().isEmpty()) {
            areaDetails += "<p>";

            areaDetails += "This band contains <font color = 'RED'><overlappingConceptCount></font> overlapping "
                    + "<conceptTypeName count=<overlappingConceptCount>> that are summarized by "
                    + "multiple <nodeTypeName count=2>.";
        }

        areaDetails = areaDetails.replaceAll("<bandName>", band.getName());
        areaDetails = areaDetails.replaceAll("<conceptCount>", Integer.toString(band.getConcepts().size()));
        areaDetails = areaDetails.replaceAll("<clusterCount>", Integer.toString(band.getClusters().size()));
        areaDetails = areaDetails.replaceAll("<overlappingConceptCount>", Integer.toString(band.getOverlappingConcepts().size()));

        if (this.getConfiguration().getAbstractionNetwork().isAggregated()) {
            areaDetails += "<p>";
            areaDetails += new AggregatePartitionedNodeTextGenerator().formatText(band, getConfiguration().getTextConfiguration());
        }

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration());

        return formatter.format(areaDetails);
    }
}
