package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.AreaTaxonomyTextConfiguration;

/**
 *
 * @author Chris Ochs
 */
public class DiffAreaTaxonomyTextConfiguration extends AreaTaxonomyTextConfiguration {
    
    
    public DiffAreaTaxonomyTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig) {

        super(ontologyEntityNameConfig);
    }
    
    
    
    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Diff Areas";
        } else {
            return "Diff Area";
        }
    }

    @Override
    public String getAbNName() {
        return "";
    }

    @Override
    public String getAbNSummary() {
        return "";
    }

    @Override
    public String getAbNHelpDescription() {
        return "";
    }

    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Diff Area Taxonomies";
        } else {
            return "Diff Area Taxonomy";
        }
    }

    @Override
    public String getNodeHelpDescription(Area area) {
        
        DiffArea diffArea = (DiffArea)area;
        
        return "";
    }
}
