package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointNode;
import edu.njit.cs.saboc.blu.core.abn.disjoint.aggregate.AggregateDisjointAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNGenerator;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.AncestorDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.DisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.OverlappingNodeDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SubsetDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.abn.provenance.CachedAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An abstraction network where each concept is summarized by exactly one node.
 * Disjoint abstraction networks are created from the partition nodes of
 * other abstraction networks and summarize the intersections between the 
 * subhierarchies of concepts in partitioned nodes.
 * 
 * @author Chris
 * 
 * @param <T>
 * @param <PARENTABN_T>
 * @param <PARENTNODE_T>
 */
public class DisjointAbstractionNetwork<
        T extends DisjointNode<PARENTNODE_T>,
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> 
            extends AbstractionNetwork<T>

        implements AggregateableAbstractionNetwork<DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>> {
    
    private final Set<PARENTNODE_T> allNodes;
    
    private final Set<PARENTNODE_T> overlappingNodes;
    
    private final PARENTABN_T parentAbN;
    
    private final DisjointAbNFactory factory;
    
    private final int levels;
    
    private boolean isAggregated;
    
    public DisjointAbstractionNetwork(
            PARENTABN_T parentAbN, 
            Hierarchy<T> groupHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            DisjointAbNFactory factory,
            int levels,
            Set<PARENTNODE_T> allNodes,
            Set<PARENTNODE_T> overlappingNodes,
            DisjointAbNDerivation derivation) {
        
        super(groupHierarchy, sourceHierarchy, derivation);
        
        this.parentAbN = parentAbN;
                
        this.allNodes = allNodes;
        
        this.overlappingNodes = overlappingNodes;
        
        this.factory = factory;
        
        this.levels = levels;
        
        this.isAggregated = false;
    }
    
    public DisjointAbstractionNetwork(
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> disjointAbN, DisjointAbNDerivation derivation) { 
        
        this(disjointAbN.getParentAbstractionNetwork(),
                disjointAbN.getNodeHierarchy(), 
                disjointAbN.getSourceHierarchy(), 
                disjointAbN.getFactory(),
                disjointAbN.getLevelCount(), 
                disjointAbN.getAllSourceNodes(), 
                disjointAbN.getOverlappingNodes(), 
                derivation);
    }
    
    public DisjointAbstractionNetwork(
            DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> disjointAbN) {
        
        this(disjointAbN, disjointAbN.getDerivation());
    }
    
    public DisjointAbNFactory getFactory() {
        return factory;
    }
    
    @Override
    public DisjointAbNDerivation getDerivation() {
        return (DisjointAbNDerivation)super.getDerivation();
    }
    
    @Override
    public CachedAbNDerivation<DisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T>> getCachedDerivation() {
        return super.getCachedDerivation();
    }
    
    public PARENTABN_T getParentAbstractionNetwork() {
        return parentAbN;
    }
    
    public Set<PARENTNODE_T> getAllSourceNodes() {
        return allNodes;
    }
    
    public Set<PARENTNODE_T> getOverlappingNodes() {
        return overlappingNodes;
    }
    
    public Set<T> getAllDisjointNodes() {
        return super.getNodeHierarchy().getNodes();
    }
    
    public Set<T> getOverlappingDisjointNodes() {
        Set<T> allDisjointNodes = getAllDisjointNodes();
        
        Set<T> overlappingDisjointNodes = 
                allDisjointNodes.stream().filter( (disjointNode) -> {
                    return disjointNode.getOverlaps().size() > 1;
                }).collect(Collectors.toSet());
        
        return overlappingDisjointNodes;
    }
    
    public Set<T> getNonOverlappingDisjointNodes() {
        Set<T> allDisjointNodes = getAllDisjointNodes();
        
        Set<T> nonOverlappingDisjointNodes = 
                allDisjointNodes.stream().filter( (disjointNode) -> {
                    return disjointNode.getOverlaps().size() == 1;
                }).collect(Collectors.toSet());
        
        return nonOverlappingDisjointNodes;
    }

    public int getLevelCount() {
        return levels;
    }
    
    public Set<T> getRoots() {
        return super.getNodeHierarchy().getRoots();
    }

    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T node) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                node, 
                this.getSourceHierarchy(), 
                this.getAllDisjointNodes());
    }

    protected void setAggregated(boolean value) {
        this.isAggregated = value;
    }
    
    @Override
    public boolean isAggregated() {
        return isAggregated;
    }
    
    @Override
    public DisjointAbstractionNetwork getAggregated(AggregatedProperty ap) {
        AggregateDisjointAbNGenerator generator = new AggregateDisjointAbNGenerator();
        
        AggregateAbNGenerator<DisjointNode<PARENTNODE_T>, AggregateDisjointNode<PARENTNODE_T>> aggregateGenerator = new AggregateAbNGenerator<>();
        
        return generator.createAggregateDisjointAbN(this, aggregateGenerator, ap);
    }
    
    
    
    public AncestorDisjointAbN<T, PARENTABN_T, PARENTNODE_T> getAncestorDisjointAbN(T root) {
        Hierarchy<T> ancestorSubhierarchy = this.getNodeHierarchy().getAncestorHierarchy(root);
        
        Hierarchy<Concept> conceptSubhierarchy = AbstractionNetworkUtils.getConceptHierarchy(ancestorSubhierarchy, this.getSourceHierarchy());
        
        return new AncestorDisjointAbN<>(
                root, 
                this, 
                ancestorSubhierarchy, 
                conceptSubhierarchy, 
                new AncestorDisjointAbNDerivation(this.getDerivation(), root.getRoot()));
    }
    
    public SubsetDisjointAbstractionNetwork<T, PARENTABN_T, PARENTNODE_T> getSubsetDisjointAbN(Set<PARENTNODE_T> overlaps) {
        
        SubsetDisjointAbNSubhierarchyVisitor visitor = 
                new SubsetDisjointAbNSubhierarchyVisitor<>(
                        (Hierarchy<DisjointNode<PARENTNODE_T>>)this.getNodeHierarchy(), 
                        overlaps);
        
        this.getNodeHierarchy().topologicalDown(visitor);

        Hierarchy<T> subsetSubhierarchy = (Hierarchy<T>)visitor.getSubsetSubhierarchy();
        Hierarchy<Concept> conceptSubhierarchy = AbstractionNetworkUtils.getConceptHierarchy(subsetSubhierarchy, this.getSourceHierarchy());
        
        return new SubsetDisjointAbstractionNetwork<>(
                overlaps, 
                subsetSubhierarchy, 
                conceptSubhierarchy, 
                this.getFactory(),
                this,
                new SubsetDisjointAbNDerivation(getDerivation(), overlaps));
    }
    
    public OverlappingNodeDisjointAbN<T, PARENTABN_T, PARENTNODE_T> getOverlappingNodeDisjointAbN(PARENTNODE_T overlappingNode) {
        
        Set<T> validNodes = new HashSet<>();
        
        this.getOverlappingDisjointNodes().forEach( (disjointNode) -> {
            if(disjointNode.getOverlaps().contains(overlappingNode)) {
                validNodes.add(disjointNode);
            }
        });
        
        Hierarchy<T> nodeSubhierarchy = this.getNodeHierarchy().getAncestorHierarchy(validNodes);
        Hierarchy<Concept> conceptSubhierarchy = AbstractionNetworkUtils.getConceptHierarchy(nodeSubhierarchy, this.getSourceHierarchy());
        
        return new OverlappingNodeDisjointAbN<>(
                overlappingNode, 
                nodeSubhierarchy, 
                conceptSubhierarchy, 
                this.getFactory(),
                this, 
                new OverlappingNodeDisjointAbNDerivation(this.getDerivation(), overlappingNode));
    }
}
