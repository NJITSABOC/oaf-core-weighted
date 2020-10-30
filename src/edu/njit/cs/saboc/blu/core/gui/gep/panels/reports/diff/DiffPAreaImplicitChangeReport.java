package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChange.ChangeInheritanceType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.utils.comparators.SinglyRootedNodeComparator;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Chris Ochs
 */
public class DiffPAreaImplicitChangeReport {
    
    private final ArrayList<DiffPArea> diffPAreasWithImplicitChanges;
    
    public DiffPAreaImplicitChangeReport(DiffPAreaTaxonomy diffTaxonomy) {

        Predicate<DiffPArea> addedRemovedFilter = (diffPArea) -> {
          return diffPArea.getPAreaState() == ChangeState.Introduced || 
                  diffPArea.getPAreaState() == ChangeState.Removed;
        };
        
        Predicate<DiffAbNConceptChange> explicitChangeFilter = (change) -> {
            return change.getChangeInheritanceType() == ChangeInheritanceType.Direct;
        };
        
        Predicate<DiffPArea> implicitRootChangesFilter = (diffPArea) -> {
            Set<DiffAbNConceptChange> conceptChanges = diffTaxonomy.getOntologyStructuralChanges().
                    getAllChangesFor(diffPArea.getRoot());
            
            return !conceptChanges.stream().anyMatch(explicitChangeFilter);
        };
        
        Set<DiffPArea> implicitlyChangedPAreas = diffTaxonomy.getPAreas().stream().
                filter(addedRemovedFilter).
                filter(implicitRootChangesFilter).
                collect(Collectors.toSet());
        
        this.diffPAreasWithImplicitChanges = new ArrayList<>(implicitlyChangedPAreas);
        this.diffPAreasWithImplicitChanges.sort(new SinglyRootedNodeComparator());
    }
    
    public ArrayList<DiffPArea> getPAreasWithOnlyImplicitChanges() {
        return this.diffPAreasWithImplicitChanges;
    }
}
 