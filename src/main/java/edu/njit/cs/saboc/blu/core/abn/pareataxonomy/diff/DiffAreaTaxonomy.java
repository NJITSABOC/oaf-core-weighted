package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * A diff abstraction network that summarizes changes to the sets of property types
 * that are used to model sets of concepts between two ontology releases
 * 
 * @author Chris O
 */
public class DiffAreaTaxonomy extends AreaTaxonomy<DiffArea> implements DiffAbstractionNetworkInstance<AreaTaxonomy> {
    
    private final DiffPAreaTaxonomyFactory diffFactory;
    
    private final DiffPAreaTaxonomyConceptChanges conceptChanges;
    
    private final AreaTaxonomy fromAreaTaxonomy;
    private final AreaTaxonomy toAreaTaxonomy;
    
    public DiffAreaTaxonomy(
            DiffPAreaTaxonomyFactory diffFactory,
            DiffPAreaTaxonomyConceptChanges conceptChanges,
            AreaTaxonomy fromAreaTaxonomy,
            AreaTaxonomy toAreaTaxonomy,
            Hierarchy<DiffArea> areaHierarchy) {
        
        super(toAreaTaxonomy.getPAreaTaxonomyFactory(), 
                areaHierarchy, 
                toAreaTaxonomy.getSourceHierarchy(), 
                null);
        
        this.diffFactory = diffFactory;
        
        this.conceptChanges = conceptChanges;
        
        this.fromAreaTaxonomy = fromAreaTaxonomy;
        this.toAreaTaxonomy = toAreaTaxonomy;
    }
    
    public DiffPAreaTaxonomyFactory getDiffFactory() {
        return diffFactory;
    }
    
    public DiffPAreaTaxonomyConceptChanges getOntologyStructuralChanges() {
        return conceptChanges;
    }
    
    public AreaTaxonomy getFrom() {
        return fromAreaTaxonomy;
    }
    
    public AreaTaxonomy getTo() {
        return toAreaTaxonomy;
    }
}
