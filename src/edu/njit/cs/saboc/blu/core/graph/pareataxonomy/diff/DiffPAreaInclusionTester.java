package edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.layout.NodeInclusionTester;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffPAreaInclusionTester implements NodeInclusionTester<PArea> {
    
    private final DiffPAreaTaxonomy diffTaxonomy;
    private final Set<ChangeState> allowedStates;
    
    public DiffPAreaInclusionTester(
            DiffPAreaTaxonomy diffTaxonomy, 
            Set<ChangeState> allowedStates) {
        
        this.diffTaxonomy = diffTaxonomy;
        this.allowedStates = allowedStates;
    }

    // A parea is included if its in the root area or if the change state is allowed
    @Override
    public boolean includeInLayout(PArea parea) {
        
        DiffPArea diffPArea = (DiffPArea)parea;
        
        boolean isRoot = diffTaxonomy.getAreaTaxonomy().getAreaHierarchy().getRoot().getPAreas().contains(diffPArea);
        boolean hasAllowedState = allowedStates.contains(diffPArea.getPAreaState());
        
        return isRoot || hasAllowedState;
    }
}