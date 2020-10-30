package edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.DiffPAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.graph.layout.NodeInclusionTester;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffAreaInclusionTester implements NodeInclusionTester<Area> {
    
    private final DiffPAreaTaxonomy diffTaxonomy;
    private final Set<ChangeState> allowedStates;
    
    public DiffAreaInclusionTester(
            DiffPAreaTaxonomy diffTaxonomy, 
            Set<ChangeState> allowedStates) {
        
        this.diffTaxonomy = diffTaxonomy;
        this.allowedStates = allowedStates;
    }

    // An area is included if its the root area or if the change state is allowed
    @Override
    public boolean includeInLayout(Area area) {
        
        DiffArea diffArea = (DiffArea)area;

        return diffTaxonomy.getAreaTaxonomy().getAreaHierarchy().getRoot().equals(diffArea) || 
                allowedStates.contains(diffArea.getAreaState());
    }
}
