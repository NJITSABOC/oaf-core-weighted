package edu.njit.cs.saboc.blu.core.gui.utils.renderers;


import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.utils.filterable.list.Filterable;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class BasicFilterableConcept<T extends Concept> extends Filterable<T> {
    
    private final T concept;
    
    public BasicFilterableConcept(T concept) {
        this.concept = concept;
    }

    @Override
    public boolean containsFilter(String filter) {
        return false;
    }

    @Override
    public T getObject() {
        return concept;
    }

    @Override
    public String getToolTipText() {
        return concept.getName();
    }

    @Override
    public String getClipboardText() {
        return String.format("%s\t%s", concept.getName(), concept.getIDAsString());
    }
}
