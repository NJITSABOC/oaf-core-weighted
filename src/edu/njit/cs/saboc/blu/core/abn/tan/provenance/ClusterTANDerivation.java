package edu.njit.cs.saboc.blu.core.abn.tan.provenance;

import edu.njit.cs.saboc.blu.core.abn.provenance.AbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.TANFactory;

/**
 * Base class for storing the arguments to create some kind of cluster TAN
 * 
 * @author Chris O
 */
public abstract class ClusterTANDerivation extends AbNDerivation<ClusterTribalAbstractionNetwork> {
    
    private final TANFactory factory;
    
    public ClusterTANDerivation(
            TANFactory factory) {

        this.factory = factory;
    }
    
    public ClusterTANDerivation(ClusterTANDerivation base) {
        this(base.getFactory());
    }

    public TANFactory getFactory() {
        return factory;
    }
    
    @Override
    public String getAbstractionNetworkTypeName() {
        return "Tribal Abstraction Network";
    }
}
