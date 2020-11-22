package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.SubAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.BandTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ExpandedSubTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;

/**
 * A TAN that is created from the subhierarchy of clusters summarized by an 
 * aggregate cluster
 * 
 * @author Chris O
 */
public class ExpandedClusterTribalAbstractionNetwork extends ClusterTribalAbstractionNetwork 
        implements SubAbstractionNetwork<ClusterTribalAbstractionNetwork> {
    
    private final ClusterTribalAbstractionNetwork aggregateSourceTAN;
    private final AggregateCluster aggregateCluster;
    
    public ExpandedClusterTribalAbstractionNetwork(
            ClusterTribalAbstractionNetwork aggregateSourceTAN,
            AggregateCluster aggregateCluster,
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<Cluster> clusterHierarchy,
            Hierarchy<Concept> conceptHierarchy) {

        super(bandTAN, 
                clusterHierarchy, 
                conceptHierarchy, 
                new ExpandedSubTANDerivation(
                        aggregateSourceTAN.getDerivation(), 
                        aggregateCluster.getRoot()));
        
        this.aggregateSourceTAN = aggregateSourceTAN;
        this.aggregateCluster = aggregateCluster;
    }
    
    public ExpandedClusterTribalAbstractionNetwork(ExpandedClusterTribalAbstractionNetwork expandedTAN) {
        this(expandedTAN.getSuperAbN(), 
                expandedTAN.getAggregatePArea(), 
                expandedTAN.getBandTAN(), 
                expandedTAN.getClusterHierarchy(), 
                expandedTAN.getSourceHierarchy());
    }
    
    @Override
    public ExpandedSubTANDerivation getDerivation() {
        return (ExpandedSubTANDerivation)super.getDerivation();
    }

    @Override
    public ClusterTribalAbstractionNetwork getSuperAbN() {
        return aggregateSourceTAN;
    }
    
    public AggregateCluster getAggregatePArea() {
        return aggregateCluster;
    }
}