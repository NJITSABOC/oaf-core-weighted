package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomyFactory;
import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivationParser.AbNParseException;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetworkFactory;

/**
 *
 * @author Hao Liu
 */
public abstract class AbNDerivationFactory {
    
    public abstract PAreaTaxonomyFactory getPAreaTaxonomyFactory() throws AbNParseException;
    public abstract TANFactory getTANFactory() throws AbNParseException;
    public abstract TargetAbstractionNetworkFactory getTargetAbNFactory() throws AbNParseException;
    
}
