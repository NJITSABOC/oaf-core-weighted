package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.DescendantTargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 *
 * @author Chris Ochs
 * 
 * @param <T>
 */
public class DescendantTargetAbN<T extends TargetGroup> extends TargetAbstractionNetwork<T> 
        implements RootedSubAbstractionNetwork<T, TargetAbstractionNetwork<T>> {
    
    private final TargetAbstractionNetwork sourceTargetAbN;
    
    public DescendantTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            TargetAbNDerivation derivation) {

        super(sourceTargetAbN.getFactory(),
                groupHierarchy, 
                sourceHierarchy, 
                derivation);
        
        this.sourceTargetAbN = sourceTargetAbN;
    }
    
    public DescendantTargetAbN(
            TargetAbstractionNetwork sourceTargetAbN,
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        this(sourceTargetAbN, 
                groupHierarchy,
                sourceHierarchy, 
                new DescendantTargetAbNDerivation(
                        sourceTargetAbN.getDerivation(), 
                        groupHierarchy.getRoot().getRoot()));
    }
    
    public DescendantTargetAbN(DescendantTargetAbN<T> subTAN) {
        this(subTAN.getSuperAbN(), 
                subTAN.getTargetGroupHierarchy(),
                subTAN.getSourceHierarchy());
    }

    @Override
    public T getSelectedRoot() {
        return this.getTargetGroupHierarchy().getRoot();
    }

    @Override
    public TargetAbstractionNetwork getSuperAbN() {
        return sourceTargetAbN;
    }
}