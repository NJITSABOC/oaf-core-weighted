package edu.njit.cs.saboc.blu.core.abn.diff.explain;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * The set of structural changes that affected the concepts between the
 * FROM and TO releases of an ontology.
 * 
 * @author Chris O
 */
public class DiffAbNConceptChanges {
    
    private final HierarchicalChanges hierarchicalChanges;
    
    public DiffAbNConceptChanges(HierarchicalChanges hierarchicalChanges) {
        this.hierarchicalChanges = hierarchicalChanges;
    }
    
    public HierarchicalChanges getHierarchicalChanges() {
        return hierarchicalChanges;
    }
    
    public Set<DiffAbNConceptChange> getAllChangesFor(Concept c) {
        return (Set<DiffAbNConceptChange>)(Set<?>)hierarchicalChanges.getChangesFor(c);
    }
}
