package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;

/**
 * An abstraction network that is a diff abstraction network
 * @author Chris O
 * @param <T>
 */
public interface DiffAbstractionNetworkInstance<T extends AbstractionNetwork> {
    
    public DiffAbNConceptChanges getOntologyStructuralChanges();
    
    public T getFrom();
    public T getTo();
}
