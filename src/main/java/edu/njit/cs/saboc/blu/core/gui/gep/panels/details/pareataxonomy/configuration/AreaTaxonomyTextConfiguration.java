package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;

/**
 *
 * @author Chris Ochs
 */
public class AreaTaxonomyTextConfiguration extends AbNTextConfiguration<Area> {
    
    public AreaTaxonomyTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig) {
        
        super(ontologyEntityNameConfig);
    }
    
    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Area Taxonomies";
        } else {
            return "Area Taxonomy";
        }
    }
    
    @Override
    public String getNodeTypeName(boolean plural) {
        if(plural) {
            return "Areas";
        } else {
            return "Area";
        }
    }
    
    @Override
    public String getNodeHelpDescription(Area node) {
        String helpDesc = "<html>An <b>area</b> represents a set of <conceptTypeName count=2> (shown below) "
                + "that are defined using the exact same types of <propertyTypeName count=2>.";

        AbNTextFormatter formatter = new AbNTextFormatter(this);

        return formatter.format(helpDesc);
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
}
