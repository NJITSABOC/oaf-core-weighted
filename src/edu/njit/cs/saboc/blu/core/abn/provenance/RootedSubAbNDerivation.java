package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Interface for defining a derivation that creates an abstraction network that
 * consists of a chosen node and hierarchically related nodes.
 * 
 * @author Chris O
 * @param <T>
 */
public interface RootedSubAbNDerivation<T extends AbNDerivation> extends SubAbNDerivation<T> {
    
    /**
     * Returns the selected root of the rooted sub abstraction network
     * @return 
     */
    public Concept getSelectedRoot();
}
