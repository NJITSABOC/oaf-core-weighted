package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.AncestorSubtaxonomyDerivation;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.provenance.PAreaTaxonomyDerivation;

/**
 * A partial-area taxonomy that consists of a selected partial-area and all 
 * of its ancestor partial-areas.
 * 
 * @author Chris O
 * @param <T>
 */
public class AncestorSubtaxonomy<T extends PArea> extends PAreaTaxonomy<T> 
        implements RootedSubAbstractionNetwork<T, PAreaTaxonomy> {
    
    private final PAreaTaxonomy superTaxonomy;
    private final T sourcePArea;
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            T sourcePArea,
            PAreaTaxonomy subTaxonomy,
            PAreaTaxonomyDerivation derivation) {

        super(subTaxonomy, derivation);
        
        this.superTaxonomy = superTaxonomy;
        this.sourcePArea = sourcePArea;
    }
    
    public AncestorSubtaxonomy(
            PAreaTaxonomy superTaxonomy,
            T sourcePArea,
            PAreaTaxonomy subTaxonomy) {

        this(superTaxonomy, 
                sourcePArea, 
                subTaxonomy, 
                new AncestorSubtaxonomyDerivation(superTaxonomy.getDerivation(), sourcePArea.getRoot()));
    }
    
    @Override
    public T getSelectedRoot() {
        return sourcePArea;
    }

    @Override
    public PAreaTaxonomy getSuperAbN() {
        return superTaxonomy;
    }
}