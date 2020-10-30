package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNFromPAreaDerivation;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class TargetAbstractionNetworkFromPArea<T extends TargetGroup> extends TargetAbstractionNetwork<T> {
    
    private final PAreaTaxonomy sourceTaxonomy;
    private final PArea sourcePArea;
    
    public TargetAbstractionNetworkFromPArea(
            TargetAbstractionNetwork targetAbN, 
            PAreaTaxonomy sourceTaxonomy, 
            PArea sourcePArea) {
        
        super(targetAbN.getFactory(),
                targetAbN.getTargetGroupHierarchy(), 
                targetAbN.getSourceHierarchy(), 
                new TargetAbNFromPAreaDerivation(
                    targetAbN.getDerivation(),
                    sourceTaxonomy.getDerivation()));
        
        this.sourceTaxonomy = sourceTaxonomy;
        this.sourcePArea = sourcePArea;
    }
    
    public PAreaTaxonomy getSourceTaxonomy() {
        return sourceTaxonomy;
    }
    
    public PArea getSourcePArea() {
        return sourcePArea;
    }
}
