package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea.PAreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffPAreaSummaryTextFactory extends PAreaSummaryTextFactory {
    
    public DiffPAreaSummaryTextFactory(DiffPAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }

    @Override
    public String createNodeSummaryText(PArea parea) {
        DiffPAreaTaxonomyConfiguration config = this.getConfiguration();
        
        DiffPAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        DiffPArea diffPArea = (DiffPArea)parea;
        
        return getDetailsString(diffPArea);
    }
    
    private String getDetailsString(DiffPArea parea) {
        
        if(parea.getPAreaState() == ChangeState.Introduced) {
            return getIntroducedPAreaString(parea);
        } else if (parea.getPAreaState() == ChangeState.Removed) {
            return getRemovedPAreaString(parea);
        } else if (parea.getPAreaState() == ChangeState.Modified) {
            return getModifiedPAreaString(parea);
        } else {
            return getUnmodifiedPAreaString(parea);
        }
        
    }
    
    private String getUnmodifiedPAreaString(DiffPArea parea) {
        
        String result = String.format("<html><b>%s</b> is an <b>unmodified</b> diff partial-area. "
                + "This represents a subhierarchy of "
                + "<font color='RED'><conceptCount></font> <conceptTypeName count=<conceptCount>> "
                + "that underwent no changes to the set of <propertyTypeName count=2> used in their modeling.", parea.getName());
        
        result = result.replaceAll("<conceptCount>", Integer.toString(parea.getConceptCount()));
        
        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration()); 
        
        return formatter.format(result);
    }
    
    private String getModifiedPAreaString(DiffPArea parea) {
        
        String result = String.format("<html><b>%s</b> is a <b>modified</b> diff partial-area. "
                + "This represents a subhierarchy of "
                + "<font color='RED'><conceptCount></font> <conceptTypeName count=<conceptCount>>, all "
                + "modeled with the same types of <propertyTypeName count=2>, and the set of "
                + "<conceptTypeName count=2> in the subhierarchy changed "
                + "(e.g., <conceptTypeName count=2> were added or removed from the subhierarchy).", parea.getName());
    
        result = result.replaceAll("<conceptCount>", Integer.toString(parea.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration()); 
        
        return formatter.format(result);
    }
    
    private String getIntroducedPAreaString(DiffPArea parea) {
        
        String result = String.format("<html><b>%s</b> is an <b>introduced</b> diff partial-area. "
                + "This represents a subhierarchy of <font color='RED'><conceptCount></font> "
                + "<conceptTypeName count=<conceptCount>> that are all modeled with the same types "
                + "of <propertyTypeName count=2>. This subhierarchy previously did not exist "
                + "in the subhierarchy, though the individual <conceptTypeName count=2> may have "
                + "existed in other subhierarchies (e.g., with fewer or more <propertyTypeName> types).", 
                parea.getName());
    
        result = result.replaceAll("<conceptCount>", Integer.toString(parea.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration()); 
        
        return formatter.format(result);
    }
    
    private String getRemovedPAreaString(DiffPArea parea) {
        
        String result = String.format("<html><b>%s</b> is a <b>removed</b> diff partial-area. "
                + "This represents a subhierarchy of <font color='RED'><conceptCount></font> "
                + "<conceptTypeName count=<conceptCount>> that were all modeled with the same types "
                + "of <propertyTypeName count=2>. This subhierarchy no longer exists "
                + "in the subhierarchy, though the individual <conceptTypeName count=2> may have "
                + "moved to other subhierarchies (e.g., with fewer or more <propertyTypeName> types).", 
                parea.getName());
    
        result = result.replaceAll("<conceptCount>", Integer.toString(parea.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration()); 
        
        return formatter.format(result);
    }
    
}
