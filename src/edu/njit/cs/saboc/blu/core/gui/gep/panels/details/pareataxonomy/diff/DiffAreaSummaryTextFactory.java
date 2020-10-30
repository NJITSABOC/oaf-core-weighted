package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbNTextFormatter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.area.AreaSummaryTextFactory;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffAreaSummaryTextFactory extends AreaSummaryTextFactory {
    
    public DiffAreaSummaryTextFactory(DiffPAreaTaxonomyConfiguration config) {
        super(config);
    }
    
    @Override
    public DiffPAreaTaxonomyConfiguration getConfiguration() {
        return (DiffPAreaTaxonomyConfiguration)super.getConfiguration();
    }
    
    @Override
    public String createNodeSummaryText(Area area) {
        DiffPAreaTaxonomyConfiguration config = this.getConfiguration();

        DiffArea diffArea = (DiffArea) area;

        return getDetailsString(diffArea);
    }

    private String getDetailsString(DiffArea area) {

        if (area.getAreaState() == ChangeState.Introduced) {
            return getIntroducedPAreaString(area);
        } else if (area.getAreaState() == ChangeState.Removed) {
            return getRemovedPAreaString(area);
        } else if (area.getAreaState() == ChangeState.Modified) {
            return getModifiedPAreaString(area);
        } else {
            return getUnmodifiedPAreaString(area);
        }

    }

    private String getUnmodifiedPAreaString(DiffArea area) {

        String result = String.format("<html><b>%s</b> is an <b>unmodified</b> diff area. "
                + "This represents a set of "
                + "<font color='RED'><conceptCount></font> <conceptTypeName count=<conceptCount>> "
                + "that underwent no changes to the set of <propertyTypeName count=2> used in their modeling.", area.getName());

        result = result.replaceAll("<conceptCount>", Integer.toString(area.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration());

        return formatter.format(result);
    }

    private String getModifiedPAreaString(DiffArea area) {

        String result = String.format("<html><b>%s</b> is a <b>modified</b> diff area. "
                + "This represents a set of "
                + "<font color='RED'><conceptCount></font> <conceptTypeName count=<conceptCount>>, all "
                + "modeled with the same types of <propertyTypeName count=2>, and the set of "
                + "<conceptTypeName count=2> changed "
                + "(e.g., <conceptTypeName count=2> were added or removed from the subhierarchy).", area.getName());

        result = result.replaceAll("<conceptCount>", Integer.toString(area.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration());

        return formatter.format(result);
    }

    private String getIntroducedPAreaString(DiffArea area) {

        String result = String.format("<html><b>%s</b> is an <b>introduced</b> diff area. "
                + "This represents a set of <font color='RED'><conceptCount></font> "
                + "<conceptTypeName count=<conceptCount>> that are all modeled with the same types "
                + "of <propertyTypeName count=2>. This set of <conceptTypeName count=2> with the same types of "
                + "<propertyTypeName count=2> previously did not exist in the subhierarchy. "
                + "The individual <conceptTypeName count=2> may have previously "
                + "existed in sets (e.g., with fewer or more <propertyTypeName> types).",
                area.getName());

        result = result.replaceAll("<conceptCount>", Integer.toString(area.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration());

        return formatter.format(result);
    }

    private String getRemovedPAreaString(DiffArea area) {

        String result = String.format("<html><b>%s</b> is a <b>removed</b> diff area. "
                + "This represents a set of <font color='RED'><conceptCount></font> "
                + "<conceptTypeName count=<conceptCount>> that were all modeled with the same types "
                + "of <propertyTypeName count=2> but no such <conceptTypeName> currently exists. "
                + "The individual <conceptTypeName count=2> that had these <propertyTypeName> types may have "
                + "moved to other subhierarchies (e.g., with fewer or more <propertyTypeName> types).",
                area.getName());

        result = result.replaceAll("<conceptCount>", Integer.toString(area.getConceptCount()));

        AbNTextFormatter formatter = new AbNTextFormatter(this.getConfiguration().getTextConfiguration());

        return formatter.format(result);
    }
}
