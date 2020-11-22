package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.abn.diff.utils.SetUtilities;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 */
public class SubhierarchySearcher<T extends Concept> implements OntologySearcher<T> {
    
    private final OntologySearcher<T> searcher;
    
    private final Set<T> subhierarchyConcepts;
    
    public SubhierarchySearcher(OntologySearcher<T> searcher, Hierarchy<T> subhierarchy) {
        this.searcher = searcher;
        this.subhierarchyConcepts = subhierarchy.getNodes();
    }

    @Override
    public Set<T> searchStarting(String query) {
        return filterSubhierarchyConcepts(searcher.searchStarting(query));
    }

    @Override
    public Set<T> searchExact(String query) {
        return filterSubhierarchyConcepts(searcher.searchExact(query));
    }

    @Override
    public Set<T> searchAnywhere(String query) {
        return filterSubhierarchyConcepts(searcher.searchAnywhere(query));
    }

    @Override
    public Set<T> searchID(String query) {
        return filterSubhierarchyConcepts(searcher.searchID(query));
    }

    private Set<T> filterSubhierarchyConcepts(Set<T> results) {
        return SetUtilities.getSetIntersection(results, subhierarchyConcepts);
    }
    
    
}
