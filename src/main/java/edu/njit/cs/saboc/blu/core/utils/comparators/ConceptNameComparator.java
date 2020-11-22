package edu.njit.cs.saboc.blu.core.utils.comparators;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Comparator;

/**
 *
 * @author Chris O
 */
public class ConceptNameComparator<T extends Concept> implements Comparator<T> {

    @Override
    public int compare(T a, T b) {
        return a.getName().compareToIgnoreCase(b.getName());
    }
    
}
