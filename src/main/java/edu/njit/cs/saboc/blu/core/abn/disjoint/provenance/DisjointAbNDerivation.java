package edu.njit.cs.saboc.blu.core.abn.disjoint.provenance;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;

/**
 * Stores the arguments needed to create a disjoint abstraction network.
 * 
 * @author Chris O
 */
public abstract class DisjointAbNDerivation extends AbNDerivation<DisjointAbstractionNetwork> {
    
    private final DisjointAbNFactory factory;
    
    public DisjointAbNDerivation(DisjointAbNFactory factory) {        
        this.factory = factory;
    }
    
    public DisjointAbNDerivation(DisjointAbNDerivation derivedAbN) {
        this(derivedAbN.getFactory());
    }
    
    public DisjointAbNFactory getFactory() {
        return factory;
    }
}
