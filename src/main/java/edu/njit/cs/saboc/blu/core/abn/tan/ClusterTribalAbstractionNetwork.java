package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.CachedAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.aggregate.AggregateClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.ClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.abn.tan.provenance.SimpleClusterTANDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 * An abstraction network that summarizes subhierarchies of concepts that exist 
 * at specific points of intersection among two or more patriarch-defined
 * subhierarchies. Consists of a hierarchy of clusters embedded within a 
 * hierarchy of Bands (i.e., a Band Tribal Abstraction Network).s
 * 
 * @author Chris
 * @param <T>
 */
public class ClusterTribalAbstractionNetwork<T extends Cluster> extends PartitionedAbstractionNetwork<T, Band> 
        implements AggregateableAbstractionNetwork<ClusterTribalAbstractionNetwork<T>>{
    
    public ClusterTribalAbstractionNetwork(
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            ClusterTANDerivation derivation) {

        super(bandTAN, 
                clusterHierarchy, 
                sourceHierarchy, 
                derivation);
    }
    
    public ClusterTribalAbstractionNetwork(
            BandTribalAbstractionNetwork bandTAN,
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy) {

        this(bandTAN, 
                clusterHierarchy, 
                sourceHierarchy, 
                new SimpleClusterTANDerivation(
                        sourceHierarchy.getRoots(), 
                        bandTAN.getSourceFactory()));
    }

    public ClusterTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan) {
        this(tan.getBandTAN(), 
                tan.getClusterHierarchy(), 
                tan.getSourceHierarchy(), 
                tan.getDerivation());
    }
    
    public ClusterTribalAbstractionNetwork(ClusterTribalAbstractionNetwork tan, ClusterTANDerivation derivation) {
        this(tan.getBandTAN(), 
                tan.getClusterHierarchy(), 
                tan.getSourceHierarchy(), 
                derivation);
    }
    
    @Override
    public ClusterTANDerivation getDerivation() {
        return (ClusterTANDerivation) super.getDerivation();
    }
    
    @Override
    public CachedAbNDerivation<ClusterTribalAbstractionNetwork> getCachedDerivation() {
        return super.getCachedDerivation();
    }
    
    public BandTribalAbstractionNetwork getBandTAN() {
        return (BandTribalAbstractionNetwork)super.getBaseAbstractionNetwork();
    }
    
    public TANFactory getSourceFactory() {
        return getBandTAN().getSourceFactory();
    }

    public Set<T> getPatriarchClusters() {
        return super.getNodeHierarchy().getRoots();
    }
    
    public Band getBandFor(T cluster) {
        return super.getPartitionNodeFor(cluster);
    }
    
    public Set<Band> getBands() {
        return super.getBaseAbstractionNetwork().getNodes();
    }

    public Set<T> getClusters() {
        return super.getNodes();
    }
    
    public Hierarchy<T> getClusterHierarchy() {
        return super.getNodeHierarchy();
    }
    
    public Set<T> getNonOverlappingPatriarchClusters() {
        Set<T> nonoverlappingPatriarchClusters = new HashSet<>();
                
        getPatriarchClusters().forEach( (patriarchCluster) -> {           
            Concept root = patriarchCluster.getRoot();
            
            if(!getClusters().stream().anyMatch( (cluster) -> {
                return cluster.getPatriarchs().size() != 1 && cluster.getPatriarchs().contains(root);
            })) {
                nonoverlappingPatriarchClusters.add(patriarchCluster);
            }
        });
        
        return nonoverlappingPatriarchClusters;
    }
    
    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T cluster) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                cluster, 
                this.getSourceHierarchy(),
                this.getClusters());
    }
    
    
    public RootSubTAN createRootSubTAN(T root) {
        Hierarchy<T> subhierarchy = this.getClusterHierarchy().getSubhierarchyRootedAt(root);

        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();
        
        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(
                subhierarchy, 
                this.getSourceHierarchy(),
                this.getSourceFactory());       

        return new RootSubTAN(this, tan.getBandTAN(), tan.getClusterHierarchy(), tan.getSourceHierarchy());
    }
    
    public AncestorSubTAN createAncestorTAN(T source) {
        
        Hierarchy<T> subhierarchy = this.getClusterHierarchy().getAncestorHierarchy(source);
        
        TribalAbstractionNetworkGenerator generator = new TribalAbstractionNetworkGenerator();

        ClusterTribalAbstractionNetwork tan = generator.createTANFromClusters(
                subhierarchy, 
                this.getSourceHierarchy(),
                this.getSourceFactory());
                
        return new AncestorSubTAN(this, source, tan.getBandTAN(), tan.getClusterHierarchy(), tan.getSourceHierarchy());
    }
        
    @Override
    public boolean isAggregated() {
        return false;
    }

    @Override
    public ClusterTribalAbstractionNetwork<T> getAggregated(AggregatedProperty ap) {
        return AggregateClusterTribalAbstractionNetwork.generateAggregatedClusterTAN(this, ap);
    }
}
