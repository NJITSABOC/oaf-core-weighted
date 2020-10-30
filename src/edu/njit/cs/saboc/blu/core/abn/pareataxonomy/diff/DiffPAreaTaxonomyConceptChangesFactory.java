package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbNConceptChangesFactory;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.InheritablePropertyChanges;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain.PropertyChangeDetailsFactory;

/**
 * Factory class for creating diff partial-area taxonomy specific structural change 
 * information that affected the concepts in a subhierarchy
 * 
 * @author Chris O
 */
public class DiffPAreaTaxonomyConceptChangesFactory implements DiffAbNConceptChangesFactory {
    
    private final PropertyChangeDetailsFactory propertyChangesFactory;
    
    private final PAreaTaxonomy fromTaxonomy;
    private final PAreaTaxonomy toTaxonomy;
    
    public DiffPAreaTaxonomyConceptChangesFactory(
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy,
            PropertyChangeDetailsFactory propertyChangesFactory) {
        
        this.propertyChangesFactory = propertyChangesFactory;
        
        this.fromTaxonomy = fromTaxonomy;
        this.toTaxonomy = toTaxonomy;
    }
    
    @Override
    public DiffAbNConceptChanges getConceptChanges(HierarchicalChanges hierarchyChanges) {
        
        InheritablePropertyChanges propertyChanges = new InheritablePropertyChanges(
                fromTaxonomy,
                toTaxonomy,
                hierarchyChanges, 
                propertyChangesFactory);
        
        return new DiffPAreaTaxonomyConceptChanges(hierarchyChanges, propertyChanges);
    }
}