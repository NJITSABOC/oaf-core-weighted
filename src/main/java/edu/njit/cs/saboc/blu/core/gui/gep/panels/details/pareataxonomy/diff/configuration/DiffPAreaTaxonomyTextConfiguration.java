package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyTextConfiguration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public abstract class DiffPAreaTaxonomyTextConfiguration extends PAreaTaxonomyTextConfiguration {
    
    private final DiffAreaTaxonomyTextConfiguration diffAreaTaxonomyTextConfig;
    
    public DiffPAreaTaxonomyTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig, 
            DiffPAreaTaxonomy taxonomy) {
        
        super(ontologyEntityNameConfig, taxonomy);
        
        diffAreaTaxonomyTextConfig =  new DiffAreaTaxonomyTextConfiguration(
                this.getOntologyEntityNameConfiguration());
    }

    @Override
    public DiffAreaTaxonomyTextConfiguration getBaseAbNTextConfiguration() {
        return diffAreaTaxonomyTextConfig;
    }

    @Override
    public DiffPAreaTaxonomy getPAreaTaxonomy() {
        return (DiffPAreaTaxonomy)super.getPAreaTaxonomy();
    }

    @Override
    public String getAbNName() {
        return "Diff Partial-area Taxonomy";
    }

    @Override
    public String getAbNSummary() {
        
        DiffPAreaTaxonomy diffTaxonomy = this.getPAreaTaxonomy();
        
        Set<DiffArea> diffAreas = diffTaxonomy.getDiffAreas();
        
        Map<ChangeState, Set<DiffArea>> diffAreasByType = new HashMap<>();
        
        diffAreas.forEach( (diffArea) -> {
            if(!diffAreasByType.containsKey(diffArea.getAreaState())) {
                diffAreasByType.put(diffArea.getAreaState(), new HashSet<>());
            }
            
            diffAreasByType.get(diffArea.getAreaState()).add(diffArea);
        });
        
        
        Map<ChangeState, Set<DiffPArea>> diffPAreasByType = new HashMap<>();
        
        diffTaxonomy.getPAreas().forEach( (diffPArea) -> {
            if(!diffPAreasByType.containsKey(diffPArea.getPAreaState())) {
                diffPAreasByType.put(diffPArea.getPAreaState(), new HashSet<>());
            }
            
            diffPAreasByType.get(diffPArea.getPAreaState()).add(diffPArea);
        });
        
        
        StringBuilder diffTaxonomySummaryStr = new StringBuilder("Diff Areas:<br>");
        
        diffAreasByType.forEach( (type, diffAreasOfType) -> {
            diffTaxonomySummaryStr.append(String.format("%s: %d<br>", type, diffAreasOfType.size()));
        });
        
        diffTaxonomySummaryStr.append("<br>Diff Partial-areas:<br>");
        
        diffPAreasByType.forEach((type, diffPAreasOfType) -> {
            diffTaxonomySummaryStr.append(String.format("%s: %d<br>", type, diffPAreasOfType.size()));
        });
        

       return diffTaxonomySummaryStr.toString();
    }
    

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Diff Partial-area Taxonomies";
        } else {
            return "Diff Partial-area Taxonomy";
        }
    }
    
    @Override
    public String getDisjointNodeTypeName(boolean plural) {
        if(plural) {
            return "Diff Disjoint partial-areas";
        } else {
            return "Diff Disjoint partial-area";
        }
    }

    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Diff Partial-areas";
        } else {
            return "Diff Partial-area";
        }
    }
}
