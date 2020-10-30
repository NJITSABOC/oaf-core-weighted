package edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.parea;

import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OAFAbstractTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris O
 */
public class ParentPAreaTableModel extends OAFAbstractTableModel<ParentNodeDetails<PArea>> {
    
    private final PAreaTaxonomyConfiguration config;
    
    public ParentPAreaTableModel(PAreaTaxonomyConfiguration config) {
        super(new String [] {
            config.getTextConfiguration().getOntologyEntityNameConfiguration().getParentConceptTypeName(false),
            "Parent Partial-area",
            String.format("# %s", config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true)),
            "Area"
        });
        
        this.config = config;
    }
    
    public Concept getParentConcept(int row) {
        return this.getItemAtRow(row).getParentConcept();
    }
    
    public PArea getParentPArea(int row) {
        return (PArea)this.getItemAtRow(row).getParentNode();
    }

    @Override
    protected Object[] createRow(ParentNodeDetails<PArea> parentInfo) {
        
        PAreaTaxonomy<PArea> taxonomy = config.getPAreaTaxonomy();
        Area area = taxonomy.getPartitionNodeFor(parentInfo.getParentNode());
        
        return new Object [] {
            parentInfo.getParentConcept().getName(), 
            parentInfo.getParentNode().getName(),
            parentInfo.getParentNode().getConceptCount(),
            area.getName("\n")
        };
    }
}