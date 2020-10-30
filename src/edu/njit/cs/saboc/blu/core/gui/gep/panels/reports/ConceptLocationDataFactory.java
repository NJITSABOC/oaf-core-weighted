package edu.njit.cs.saboc.blu.core.gui.gep.panels.reports;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface ConceptLocationDataFactory<T extends Concept> {
    public Set<T> getConceptsFromIds(ArrayList<String> ids);
}
