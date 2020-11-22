package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.disjoint.DisjointSubjectSubtaxonomyGenerator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.NodeSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.text.AggregatePartitionedNodeTextGenerator;

/**
 *
 * @author Chris O
 */
public class AreaSummaryTextFactory extends NodeSummaryTextFactory<Area> {

    public AreaSummaryTextFactory(PAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public PAreaTaxonomyConfiguration getConfiguration() {
        return (PAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public String createNodeSummaryText(Area area) {

        String areaDetails = "<html><b><areaName></b> is an area that summarizes "
                + "<font color = 'RED'><conceptCount></font> <conceptTypeName count=<conceptCount>> "
                + "in <font color = 'RED'><pareaCount></font> <nodeTypeName count=<pareaCount>>.";
        
        if(!area.getOverlappingConcepts().isEmpty()) {
            areaDetails += "<p>";
            
            areaDetails += "This area contains <font color = 'RED'><overlappingConceptCount></font> overlapping "
                + "<conceptTypeName count=<overlappingConceptCount>> that are summarized by "
                + "multiple <nodeTypeName count=2>.";
        }
        
        areaDetails = areaDetails.replaceAll("<areaName>", area.getName());
        areaDetails = areaDetails.replaceAll("<conceptCount>", Integer.toString(area.getConcepts().size()));
        areaDetails = areaDetails.replaceAll("<pareaCount>", Integer.toString(area.getPAreas().size()));
        areaDetails = areaDetails.replaceAll("<overlappingConceptCount>", Integer.toString(area.getOverlappingConcepts().size()));
        
        if(this.getConfiguration().getPAreaTaxonomy().isAggregated()) {
            areaDetails += "<p>";
            areaDetails += new AggregatePartitionedNodeTextGenerator().formatText(area, getConfiguration().getTextConfiguration());
        }
        
        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration());
       
        return formatter.format(areaDetails);
    }
}
