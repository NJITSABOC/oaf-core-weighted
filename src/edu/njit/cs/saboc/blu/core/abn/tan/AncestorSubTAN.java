package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.RootedSubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.AncestorSubTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * Defines a TAN created from a selected cluster and all of its ancestor
 * clusters
 * 
 * @author Chris O
 * @param <T>
 */
public class AncestorSubTAN<T extends Cluster> extends ClusterTribalAbstractionNetwork<T> 
        implements RootedSubAbstractionNetwork<T, ClusterTribalAbstractionNetwork<T>> {
    
    private final ClusterTribalAbstractionNetwork sourceTAN;
    private final T sourceCluster;
    
    public AncestorSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            T sourceCluster,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            ClusterTANDerivation derivation) {

        super(bandTAN, 
                clusterHierarchy, 
                sourceHierarchy, 
                derivation);
        
        this.sourceTAN = sourceTAN;
        this.sourceCluster = sourceCluster;
    }
    
    public AncestorSubTAN(
            ClusterTribalAbstractionNetwork sourceTAN,
            T sourceCluster,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        this(sourceTAN, 
                sourceCluster,
                bandTAN, 
                clusterHierarchy,
                sourceHierarchy, 
                new AncestorSubTANDerivation(
                        sourceTAN.getDerivation(), 
                        sourceCluster.getRoot()));
    }
    
    public AncestorSubTAN(AncestorSubTAN<T> subTAN) {
        this(subTAN.getSuperAbN(), 
                subTAN.getSelectedRoot(), 
                subTAN.getBandTAN(), 
                subTAN.getClusterHierarchy(), 
                subTAN.getSourceHierarchy());
    }

    @Override
    public T getSelectedRoot() {
        return sourceCluster;
    }

    @Override
    public ClusterTribalAbstractionNetwork getSuperAbN() {
        return sourceTAN;
    }
}
