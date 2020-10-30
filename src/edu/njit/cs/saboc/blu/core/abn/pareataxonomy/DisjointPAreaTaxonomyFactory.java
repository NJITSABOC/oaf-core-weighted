package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A factory class for creating disjoint partial-area taxonomies
 * 
 * @author Chris O
 */
public class DisjointPAreaTaxonomyFactory implements DisjointAbNFactory<PArea, DisjointPArea> {

    @Override
    public DisjointPArea createDisjointNode(Hierarchy<Concept> hierarchy, Set<PArea> overlaps) {
        return new DisjointPArea(hierarchy, overlaps);
    }
}
