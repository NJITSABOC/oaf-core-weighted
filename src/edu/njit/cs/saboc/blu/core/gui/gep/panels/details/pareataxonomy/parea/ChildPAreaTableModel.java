
package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;

/**
 *
 * @author Chris O
 */
public class ChildPAreaTableModel extends OAFAbstractTableModel<PArea> {

    private final PAreaTaxonomyConfiguration config;
    
    public ChildPAreaTableModel(PAreaTaxonomyConfiguration config) {
        super(new String [] {
            "Child Partial-area",
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Area"
        });
        
        this.config = config;
    }
    
    public PArea getChildPArea(int row) {
        return (PArea)this.getItemAtRow(row);
    }

    @Override
    protected Object[] createRow(PArea parea) {
        PAreaTaxonomy<PArea> taxonomy = config.getPAreaTaxonomy();
        
        Area area = taxonomy.getPartitionNodeFor(parea);
        
        return new Object [] {
            parea.getName(),
            parea.getConceptCount(),
            area.getName("\n")
        };
    }
}
