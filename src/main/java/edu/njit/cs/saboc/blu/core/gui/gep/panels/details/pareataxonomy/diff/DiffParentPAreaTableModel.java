package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.diff.configuration.DiffPAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class DiffParentPAreaTableModel extends OAFAbstractTableModel<ParentNodeDetails<DiffPArea>> {
    
    private final DiffPAreaTaxonomyConfiguration config;
    
    public DiffParentPAreaTableModel(DiffPAreaTaxonomyConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false),
            "Parent Partial-area",
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Area",
            "Diff State"
        });
        
        this.config = config;
    }
    
    public Concept getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public DiffPArea getParentPArea(int row) {
        return (DiffPArea)this.getItemAtRow(row).getParentNode();
    }

    @Override
    protected Object[] createRow(ParentNodeDetails<DiffPArea> parentInfo) {
        
        DiffPAreaTaxonomy taxonomy = config.getPAreaTaxonomy();
        Area area = taxonomy.getPartitionNodeFor(parentInfo.getParentNode());
        
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount(),
            area.getName("\n"),
            parentInfo.getParentNode().getPAreaState()
        };
    }
}