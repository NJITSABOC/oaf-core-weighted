package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard;

import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 *
 * @author Chris O
 * @param <T>
 */
public interface OntologySearcher<T extends Concept> {
    public Set<T> searchStarting(String query);
    public Set<T> searchExact(String query);
    public Set<T> searchAnywhere(String query);
    public Set<T> searchID(String query);
}
