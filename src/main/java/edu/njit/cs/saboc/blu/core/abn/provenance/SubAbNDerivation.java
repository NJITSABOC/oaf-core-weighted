package edu.njit.cs.saboc.blu.core.abn.provenance;

/**
 * An interface for defining a derivation for an abstraction network
 * that is created from another abstraction network
 * 
 * @author Chris O
 * @param <T>
 */
public interface SubAbNDerivation<T extends AbNDerivation> {
    
    /**
     * The derivation of the abstraction network the sub abstraction network
     * was derived from
     * 
     * @return 
     */
    public T getSuperAbNDerivation();
}
