package edu.njit.cs.saboc.blu.core.graph.pareataxonomy.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.change.ChangeState;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class DiffTaxonomySubsetOptions {
    
    private final Set<ChangeState> allowedDiffAreaStates;
    private final Set<ChangeState> allowedDiffPAreaStates;
    
    public DiffTaxonomySubsetOptions(
            Set<ChangeState> allowedDiffAreaStates,
            Set<ChangeState> allowedDiffPAreaStates) {
        
        this.allowedDiffAreaStates = allowedDiffAreaStates;
        this.allowedDiffPAreaStates = allowedDiffPAreaStates;
    }
    
    public Set<ChangeState> getAllowedDiffAreaStates() {
        return this.allowedDiffAreaStates;
    }
    
    public Set<ChangeState> getAllowedDiffPAreaStates() {
        return this.allowedDiffPAreaStates;
    }
}
