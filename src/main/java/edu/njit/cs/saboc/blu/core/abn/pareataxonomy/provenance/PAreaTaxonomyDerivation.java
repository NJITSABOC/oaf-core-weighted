package edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;

/**
 * The base class for defining derivations that result in a type of 
 * partial-area taxonomy
 * 
 * @author Chris O
 */
public abstract class PAreaTaxonomyDerivation extends AbNDerivation<PAreaTaxonomy> {
    
    private final PAreaTaxonomyFactory factory;
    
    public PAreaTaxonomyDerivation(
            PAreaTaxonomyFactory factory) {
        this.factory = factory;
    }
    
    public PAreaTaxonomyDerivation(PAreaTaxonomyDerivation base) {
        this(base.getFactory());
    }

    public PAreaTaxonomyFactory getFactory() {
        return factory;
    }
    
}
