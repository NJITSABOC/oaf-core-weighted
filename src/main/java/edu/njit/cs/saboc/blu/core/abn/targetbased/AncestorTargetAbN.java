package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.AncestorTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris Ochs
 * @param <T>
 */
public class AncestorTargetAbN<T extends TargetGroup> extends TargetAbstractionNetwork<T> 
        implements RootedSubAbstractionNetwork<T, TargetAbstractionNetwork<T>> {
    
    private final TargetAbstractionNetwork sourceTargetAbN;
    private final T sourceGroup;
    
    public AncestorTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            T sourceGroup,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            TargetAbNDerivation derivation) {

        super(sourceTargetAbN.getFactory(),
                groupHierarchy, 
                sourceHierarchy, 
                derivation);
        
        this.sourceTargetAbN = sourceTargetAbN;
        this.sourceGroup = sourceGroup;
    }
    
    public AncestorTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            T sourceGroup,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        this(sourceTargetAbN, 
                sourceGroup,
                groupHierarchy,
                sourceHierarchy, 
                new AncestorTargetAbNDerivation(
                        sourceTargetAbN.getDerivation(), 
                        sourceGroup.getRoot()));
    }
    
    public AncestorTargetAbN(AncestorTargetAbN<T> subTAN) {
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
                subTAN.getTargetGroupHierarchy(),
                subTAN.getSourceHierarchy());
    }

    @Override
    public T getSelectedRoot() {
        return sourceGroup;
    }

    @Override
    public TargetAbstractionNetwork getSuperAbN() {
        return sourceTargetAbN;
    }
}