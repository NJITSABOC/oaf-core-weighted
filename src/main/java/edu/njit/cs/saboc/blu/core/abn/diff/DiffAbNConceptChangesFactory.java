package edu.njit.cs.saboc.blu.core.abn.diff;

import edu.njit.cs.saboc.blu.core.abn.diff.explain.DiffAbNConceptChanges;
import edu.njit.cs.saboc.blu.core.abn.diff.explain.HierarchicalChanges;

/**
 * An interface for defining a factory that creates an object that stores 
 * various structural changes that occurred between two ontology releases.
 * 
 * @author Chris O
 */
public interface DiffAbNConceptChangesFactory {
    public DiffAbNConceptChanges getConceptChanges(HierarchicalChanges changes);
}
