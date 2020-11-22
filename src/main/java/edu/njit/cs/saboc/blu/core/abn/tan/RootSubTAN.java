package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.RootSubTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A TAN created from a selected root cluster and all of its descendant clusters
 * 
 * @author Chris O
 * @param <T>
 */
public class RootSubTAN<T extends Cluster> extends ClusterTribalAbstractionNetwork<T> 
        implements RootedSubAbstractionNetwork<T, ClusterTribalAbstractionNetwork<T>> {
    
    private final ClusterTribalAbstractionNetwork<T> sourceTAN;
    
    public RootSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            ClusterTANDerivation derivation) {

        super(bandTAN, 
                clusterHierarchy, 
                sourceHierarchy, 
                derivation);
        
        this.sourceTAN = sourceTAN;
    }
    
    public RootSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        this(sourceTAN,
                bandTAN, 
                clusterHierarchy, 
                sourceHierarchy, 
                new RootSubTANDerivation(
                        sourceTAN.getDerivation(), 
                        clusterHierarchy.getRoot().getRoot()));
        
    }

    @Override
    public T getSelectedRoot() {
        return this.getClusterHierarchy().getRoot();
    }

    @Override
    public ClusterTribalAbstractionNetwork<T> getSuperAbN() {
        return sourceTAN;
    }
}
