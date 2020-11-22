package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.DiffAbstractionNetworkInstance;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import java.util.Set;

/**
 * A diff abstraction network that summarizes the changes in the introduction
 * and inheritance of inheritable properties from one version of an ontology
 * to another.
 * 
 * @author Chris O
 */
public class DiffPAreaTaxonomy extends PAreaTaxonomy<DiffPArea> implements DiffAbstractionNetworkInstance<PAreaTaxonomy>{
    
    private final PAreaTaxonomy fromTaxonomy;
    private final PAreaTaxonomy toTaxonomy;
    
    public DiffPAreaTaxonomy(
            DiffAreaTaxonomy areaTaxonomy,
            PAreaTaxonomy fromTaxonomy,
            PAreaTaxonomy toTaxonomy,
            Hierarchy<DiffPArea> pareaHierarchy) {

        super(areaTaxonomy, pareaHierarchy, toTaxonomy.getSourceHierarchy(), null);
        
        this.fromTaxonomy = fromTaxonomy;
        this.toTaxonomy = toTaxonomy;
    }
    
    public DiffPAreaTaxonomy(DiffPAreaTaxonomy diffTaxonomy) {
        
        this(   diffTaxonomy.getAreaTaxonomy(), 
                diffTaxonomy.getFrom(), 
                diffTaxonomy.getTo(), 
                diffTaxonomy.getPAreaHierarchy());
    }
    
    @Override
    public DiffAreaTaxonomy getAreaTaxonomy() {
        return (DiffAreaTaxonomy)super.getAreaTaxonomy();
    }

    public Set<DiffArea> getDiffAreas() {
        return getAreaTaxonomy().getAreas();
    }

    @Override
    public DiffPArea getRootPArea() {
        return (DiffPArea)super.getRootPArea();
    }

    @Override
    public DiffArea getAreaFor(DiffPArea parea) {
        return (DiffArea)super.getAreaFor(parea); 
    }
    
    @Override
    public DiffPAreaTaxonomyConceptChanges getOntologyStructuralChanges() {
        return getAreaTaxonomy().getOntologyStructuralChanges();
    }
    
    @Override
    public PAreaTaxonomy getFrom() {
        return fromTaxonomy;
    }
    
    @Override
    public PAreaTaxonomy getTo() {
        return toTaxonomy;
    }
}
