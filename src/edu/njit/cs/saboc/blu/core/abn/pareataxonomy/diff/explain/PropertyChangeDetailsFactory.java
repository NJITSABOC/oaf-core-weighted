package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.diff.explain;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Map;
import java.util.Set;

/**
 * An interface for defining factory classes that create or retrieve
 * implementation-specific property change information 
 * 
 * @author Chris O
 */
public interface PropertyChangeDetailsFactory {
    public Set<InheritableProperty> getFromOntProperties();
    public Set<InheritableProperty> getToOntProperties();

    public Map<InheritableProperty, Set<Concept>> getFromDomains();
    public Map<InheritableProperty, Set<Concept>> getToDomains();
}
