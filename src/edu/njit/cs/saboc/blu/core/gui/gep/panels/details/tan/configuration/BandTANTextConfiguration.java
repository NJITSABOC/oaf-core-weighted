package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNTextConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.OntologyEntityNameConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;

/**
 *
 * @author Chris Ochs
 */
public class BandTANTextConfiguration extends AbNTextConfiguration<Band> {
    
     public BandTANTextConfiguration(
            OntologyEntityNameConfiguration ontologyEntityNameConfig) {
        
        super(ontologyEntityNameConfig);
     }
     
         
    @Override
    public String getAbNTypeName(boolean plural) {
        if(plural) {
            return "Band TANs";
        } else {
            return "Band TAN";
        }
    }
    
    @Override
    public String getNodeTypeName(boolean plural) {
        if (plural) {
            return "Bands";
        } else {
            return "Band";
        }
    }
    
    @Override
    public String getNodeHelpDescription(Band container) {
  
        String result = "A <b>band</b> summarizes the set of all <conceptTypeName count=2> that "
                + "exist at the intersection of the same subhierarchies. "
                + "The <conceptTypeName count=2> summarized by a band are all descendants of "
                + "the same patriarch <conceptTypeName count=2>. "
                + "Each <conceptTypeName count=2> is summarized by exactly one band.";

    
        AbNTextFormatter formatter = new AbNTextFormatter(this);
        
        return formatter.format(result);
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
