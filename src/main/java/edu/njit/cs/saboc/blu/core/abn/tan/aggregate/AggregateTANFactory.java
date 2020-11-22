package edu.njit.cs.saboc.blu.core.abn.tan.aggregate;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateAbNFactory;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Set;

/**
 * A factory class for creating TAN objects
 * 
 * @author Chris O
 */
public class AggregateTANFactory implements AggregateAbNFactory<Cluster, AggregateCluster> {

    @Override
    public AggregateCluster createAggregateNode(Hierarchy<Cluster> aggregatedNodes, 
            Hierarchy<Concept> sourceHierarchy) {
        
        Hierarchy<Concept> hierarchy = new Hierarchy<>(aggregatedNodes.getRoot().getRoot());
        
        aggregatedNodes.getNodes().forEach( (parea) -> {
            hierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        Set<Concept> conceptsInCluster = hierarchy.getNodes();
        
        aggregatedNodes.getNodes().forEach((parea) -> {
            Concept root = parea.getRoot();
            
            sourceHierarchy.getParents(root).forEach((parent) -> {
               if(conceptsInCluster.contains(parent)) {
                   hierarchy.addEdge(root, parent);
               } 
            });
        });
        
        return new AggregateCluster(hierarchy, aggregatedNodes.getRoot().getPatriarchs(), aggregatedNodes);
    }
}
