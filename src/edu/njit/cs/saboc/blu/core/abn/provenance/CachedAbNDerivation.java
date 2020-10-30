
package edu.njit.cs.saboc.blu.core.abn.provenance;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import edu.njit.cs.saboc.blu.core.ontology.Ontology;
import org.json.simple.JSONObject;

/**
 * A utility class for saving an abstraction network in-memory so it
 * doesn't have to be rederived. Useful for when re-deriving takes 
 * an annoying amount of time.
 * 
 * @author Chris O
 * @param <T>
 */
public class CachedAbNDerivation<T extends AbstractionNetwork> extends AbNDerivation<T> {
    private final T abn;
    
    public CachedAbNDerivation(T abn) {
        this.abn = abn;
    }

    @Override
    public String getDescription() {
        return String.format("%s (cached)", abn.getDerivation().getDescription());
    }

    @Override
    public T getAbstractionNetwork(Ontology<Concept> ontology) {
        return abn;
    }

    @Override
    public String getName() {
        return String.format("%s (cached)", abn.getDerivation().getName());
    }

    @Override
    public String getAbstractionNetworkTypeName() {
        return abn.getDerivation().getAbstractionNetworkTypeName();
    }

    public JSONObject serializeToJSON() {        
        JSONObject result = new JSONObject();
        
        return result;
    }
}
