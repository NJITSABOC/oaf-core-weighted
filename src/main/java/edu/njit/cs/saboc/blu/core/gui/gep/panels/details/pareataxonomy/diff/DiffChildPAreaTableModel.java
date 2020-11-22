
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class DiffChildPAreaTableModel extends OAFAbstractTableModel<DiffPArea> {

    private final DiffPAreaTaxonomyConfiguration config;
    
    public DiffChildPAreaTableModel(DiffPAreaTaxonomyConfiguration config) {
        super(new String [] {
            "Child Partial-area",
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Area",
            "Diff State"
        });
        
        this.config = config;
    }
    
    public DiffPArea getChildPArea(int row) {
        return (DiffPArea)this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(DiffPArea parea) {
        DiffPAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        
        Area area = taxonomy.getPartitionNodeFor(parea);
        
        return new Object [] {
            parea.getName(),
            parea.getConceptCount(),
            area.getName("\n"),
            parea.getPAreaState()
        };
    }
}
