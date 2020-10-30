package edu.njit.cs.saboc.blu.core.abn.targetbased.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.targetbased.IncomingRelationshipDetails;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Chris O
 */
public class AggregateTargetGroup extends TargetGroup implements AggregateNode<TargetGroup>  {
    
    private final Hierarchy<TargetGroup> aggregatedGroups;
    
    public AggregateTargetGroup(Hierarchy<Concept> conceptHierarchy, 
            IncomingRelationshipDetails relationships, 
            Hierarchy<TargetGroup> aggregatedGroups) {
        
        super(conceptHierarchy, relationships);
        
        this.aggregatedGroups = aggregatedGroups;
    }
    
    @Override
    public Hierarchy<TargetGroup> getAggregatedHierarchy() {
        return aggregatedGroups;
    }
    
    @Override
    public Set<TargetGroup> getAggregatedNodes() {
        Set<TargetGroup> nodes = new HashSet<>(aggregatedGroups.getNodes());
        nodes.remove(aggregatedGroups.getRoot());
        
        return nodes;
    }
}
