
package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate.AggregateTargetAbN;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateableAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.ParentNodeDetails;
import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregatedProperty;
import edu.njit.cs.saboc.blu.core.abn.provenance.CachedAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * An abstraction network that summarizes subhierarchies of concepts that are 
 * targets of a certain kind of relationship
 * 
 * @author Chris O
 * @param <T>
 */
public class TargetAbstractionNetwork<T extends TargetGroup> extends AbstractionNetwork<T> 
    implements AggregateableAbstractionNetwork<TargetAbstractionNetwork<T>> {
    
    private boolean isAggregated = false;
    
    private final TargetAbstractionNetworkFactory factory;
    
    public TargetAbstractionNetwork(
            TargetAbstractionNetworkFactory factory,
            Hierarchy<T> groupHierarchy, 
            Hierarchy<Concept> sourceHierarchy,
            TargetAbNDerivation derivation) {
        
        super(groupHierarchy, sourceHierarchy, derivation);
        
        this.factory = factory;
    }
        
    public TargetAbstractionNetwork(TargetAbstractionNetwork targetAbN) {
        
        this(targetAbN.getFactory(),
                targetAbN.getNodeHierarchy(), 
                targetAbN.getSourceHierarchy(), 
                targetAbN.getDerivation());
    }
    
    public TargetAbstractionNetworkFactory getFactory() {
        return factory;
    }
    
    @Override
    public TargetAbNDerivation getDerivation() {
        return (TargetAbNDerivation)super.getDerivation();
    }
    
    @Override
    public CachedAbNDerivation<TargetAbstractionNetwork> getCachedDerivation() {
        return super.getCachedDerivation();
    }
    
    public Set<T> getTargetGroups() {
        return super.getNodes();
    }
    
    public Hierarchy<T> getTargetGroupHierarchy() {
        return super.getNodeHierarchy();
    }

    @Override
    public Set<ParentNodeDetails<T>> getParentNodeDetails(T group) {
        return AbstractionNetworkUtils.getSinglyRootedNodeParentNodeDetails(
                group, 
                this.getSourceHierarchy(),
                this.getTargetGroups());
    }
    
    /**
     * Since target group's getConcepts() method only returns target concepts
     * its neccessary to check the entire hierarchy, including non-target
     * concepts
     * 
     * @param concept
     * @return 
     */
    public Set<TargetGroup> getTargetGroupsWith(Concept concept) {
        
        Set<TargetGroup> nodes = this.getTargetGroupHierarchy().getNodes().stream().filter( 
                (node) -> {
                    return node.getHierarchy().getNodes().contains(concept); 
                }).collect(Collectors.toSet());
        
        return nodes;
    }

    @Override
    public boolean isAggregated() {
        return isAggregated;
    }
    
    public void setAggregated(boolean value) {
        this.isAggregated = value;
    }

    @Override
    public TargetAbstractionNetwork<T> getAggregated(AggregatedProperty ap) {
        return AggregateTargetAbN.createAggregated(this, ap);
    }
    
    public TargetAbstractionNetwork createAncestorTargetAbN(T root) {
        Hierarchy<T> hierarchy = this.getTargetGroupHierarchy().getAncestorHierarchy(root);
        
        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();
        
        TargetAbstractionNetwork abn = generator.createTargetAbNFromTargetGroups(
                factory, 
                hierarchy, 
                this.getSourceHierarchy());
        
        return new AncestorTargetAbN(this, root, abn.getNodeHierarchy(), abn.getSourceHierarchy());
    }
    
    public TargetAbstractionNetwork createDescendantTargetAbN(T root) {
        Hierarchy<T> hierarchy = this.getTargetGroupHierarchy().getSubhierarchyRootedAt(root);

        TargetAbstractionNetworkGenerator generator = new TargetAbstractionNetworkGenerator();
        
        TargetAbstractionNetwork abn = generator.createTargetAbNFromTargetGroups(
                factory, 
                hierarchy, 
                this.getSourceHierarchy());

        return new DescendantTargetAbN(this, abn.getNodeHierarchy(), abn.getSourceHierarchy());
    }
}
