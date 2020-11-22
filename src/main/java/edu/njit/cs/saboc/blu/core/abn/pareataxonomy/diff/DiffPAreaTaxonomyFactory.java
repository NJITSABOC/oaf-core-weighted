package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.AreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.PropertyChangeDetailsFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;

/**
 * A factory class for creating implementation-specific diff partial-area
 * taxonomy objects.
 * 
 * @author Chris O
 */
public abstract class DiffPAreaTaxonomyFactory {
    
    public DiffAreaTaxonomy createDiffAreaTaxonomy(
            DiffPAreaTaxonomyConceptChanges conceptChanges,
            AreaTaxonomy fromSourceTaxonomy, 
            AreaTaxonomy toSourceTaxonomy, 
            Hierarchy<DiffArea> diffAreas) {
        
        return new DiffAreaTaxonomy(this, 
                conceptChanges,
                fromSourceTaxonomy,
                toSourceTaxonomy, 
                diffAreas);
    }
    
    public DiffPAreaTaxonomy createDiffPAreaTaxonomy(
            DiffAreaTaxonomy areaTaxonomy,
            PAreaTaxonomy fromSourceTaxonomy,
            PAreaTaxonomy toSourceTaxonomy,
            Hierarchy<DiffPArea> pareaHierarchy) {
        
        return new DiffPAreaTaxonomy(areaTaxonomy, 
                fromSourceTaxonomy, 
                toSourceTaxonomy, 
                pareaHierarchy);
    }
    
    public abstract PropertyChangeDetailsFactory getPropertyChangeDetailsFactory();
}
