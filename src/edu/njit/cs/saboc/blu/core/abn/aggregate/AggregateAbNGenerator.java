package edu.njit.cs.saboc.blu.core.abn.aggregate;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Generates aggregate abstraction networks. Takes as input a hierarchy of
 * non-aggregate nodes from an abstraction network and returns a hierarchy of
 * aggregated nodes that summarizes the input node hierarchy.
 * 
 * @author Chris O
 * 
 * @param <NODE_T>
 * @param <AGGREGATENODE_T>
 */
public class AggregateAbNGenerator <
        NODE_T extends Node,
        AGGREGATENODE_T extends Node & AggregateNode<NODE_T>> {
    
    /**
     * Creates a subhierarchy of aggregated nodes from the given inputs
     * 
     * @param factory
     * @param sourceHierarchy
     * @param sourceConceptHierarchy
     * @param aggregatedProperty which includes 
     * minNodeSize The minimum size node that is preserved and 
     * isWeightedAggregated flag for weighted aggregated
     * @return 
     */
    public Hierarchy<AGGREGATENODE_T> createAggregateAbN(
            AggregateAbNFactory<NODE_T, AGGREGATENODE_T> factory,
            Hierarchy<NODE_T> sourceHierarchy, 
            Hierarchy<Concept> sourceConceptHierarchy,
            AggregatedProperty aggregatedProperty
            ) {
        
        Map<NODE_T, Hierarchy<NODE_T>> aggregateNodeMembers = new HashMap<>();

        Map<NODE_T, Integer> nodeParentCount = new HashMap<>();
        Map<NODE_T, HashSet<NODE_T>> groupSet = new HashMap<>();
        
        // Step 1: Determine which nodes will be the roots of aggregate nodes
        Set<NODE_T> remainingNodes = new HashSet<>();
        remainingNodes.addAll(sourceHierarchy.getRoots()); // The roots are always included
                
        for(NODE_T group : sourceHierarchy.getNodes()) {
            nodeParentCount.put(group, sourceHierarchy.getParents(group).size());
            groupSet.put(group, new HashSet<>());
        }
        
        int threshold = aggregatedProperty.getAutoScaleBound();
        boolean isAutoScaled = aggregatedProperty.getAutoScaled();
        
        if (isAutoScaled) {
            if (threshold > remainingNodes.size()) {
                int top = threshold - remainingNodes.size();

                Map<NODE_T, Integer> remaingNodesWithCount = new HashMap<>();

                for (NODE_T group : sourceHierarchy.getNodes()) {
                    if (remainingNodes.contains(group)) 
                        continue;
                    int allDescendantsConceptCount = 0;
                    for (NODE_T descendant : sourceHierarchy.getDescendants(group)) {
                        allDescendantsConceptCount = allDescendantsConceptCount + descendant.getConceptCount();
                    }
                    remaingNodesWithCount.put(group, group.getConceptCount() + allDescendantsConceptCount);
                }
                Map<NODE_T, Integer> sorted
                        = remaingNodesWithCount.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                        .limit(top)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                
//                int weightedBound = sorted.values().stream().min(Integer::compare).get();
                //TO-Do 
                // set the weighted aggregated by this smallest weighted aggregated value here.
//                aggregatedProperty.setBound(weightedBound);
//                aggregatedProperty.setWeighted(true);
           
                remainingNodes.addAll(sorted.keySet());

            } else {
                System.out.println("Hirarchy already has equal or more than " + threshold + " nodes as roots!");
            }

        }
        else{

        int minNodeSize = aggregatedProperty.getBound();
        boolean isWeighteAggregated = aggregatedProperty.getWeighted();
               
        for(NODE_T group : sourceHierarchy.getNodes()) {

//            nodeParentCount.put(group, sourceHierarchy.getParents(group).size());

            if (group.getConceptCount() >= minNodeSize) {
                remainingNodes.add(group);
            }else if (isWeighteAggregated == true) {
                int allDescendantsConceptCount=0;
                for (NODE_T descendant : sourceHierarchy.getDescendants(group)){
                    allDescendantsConceptCount = allDescendantsConceptCount + descendant.getConceptCount();                
                }
                if((group.getConceptCount() + allDescendantsConceptCount) >= minNodeSize){
                    remainingNodes.add(group);
                }
            }            
//            groupSet.put(group, new HashSet<>());
            }
        }
        
        remainingNodes.forEach((group) -> {
            aggregateNodeMembers.put(group, new Hierarchy<>(group));
        });
        
        // Step 2: Do a topological tarversal of the nodes and determine 
        // which nodes will be included in the given aggregate node
        
        // TODO: Replace with visitor...
        Queue<NODE_T> queue = new LinkedList<>();
        queue.addAll(sourceHierarchy.getRoots());
        
        while(!queue.isEmpty()) {
            
            NODE_T group = queue.remove();
            
            Set<NODE_T> parentGroups = sourceHierarchy.getParents(group);

            if (remainingNodes.contains(group)) {
                groupSet.get(group).add(group);
            } else {
                for (NODE_T parentGroup : parentGroups) {

                    // Mark that this group belongs to the same reduced group as its parents
                    groupSet.get(group).addAll(groupSet.get(parentGroup));

                    // Add this group to that reducing group too
                    groupSet.get(parentGroup).forEach((reducedGroup) -> {
                        aggregateNodeMembers.get(reducedGroup).addEdge(group, parentGroup);
                    });
                }
            }

            Set<NODE_T> childGroups = sourceHierarchy.getChildren(group);

            if (!childGroups.isEmpty()) {
                childGroups.forEach((childGroup) -> {
                    int childParentCount = nodeParentCount.get(childGroup);
                    
                    if(childParentCount - 1 == 0) {
                        queue.add(childGroup);
                    } else {
                        nodeParentCount.put(childGroup, childParentCount - 1);
                    }
                });
            }
        }
        
        // Step 3: Create the aggregate nodes
        HashMap<NODE_T, AGGREGATENODE_T> aggregateGroups = new HashMap<>();
        
        remainingNodes.forEach((aggregateGroup) -> {
            aggregateGroups.put(aggregateGroup, 
                    factory.createAggregateNode(aggregateNodeMembers.get(aggregateGroup), sourceConceptHierarchy));
        });
        
        Set<AGGREGATENODE_T> rootAggregateNodes = new HashSet<>();
       
        sourceHierarchy.getRoots().forEach((root) -> {
            rootAggregateNodes.add(aggregateGroups.get(root));
        });
        
        // Step 4: Build the aggregate node hierarchy
        Hierarchy<AGGREGATENODE_T> aggregateNodeHierarchy = new Hierarchy<>(rootAggregateNodes);
        
        aggregateGroups.values().forEach((aggregateNode) -> {
            
            if (!aggregateNodeHierarchy.getRoots().contains(aggregateNode)) {
                AggregateNode<NODE_T> theNode = (AggregateNode<NODE_T>) aggregateNode;

                NODE_T rootNode = theNode.getAggregatedHierarchy().getRoot();
                
                Set<NODE_T> parentNodes = sourceHierarchy.getParents(rootNode);
                
                parentNodes.forEach( (parentNode) -> {
                     Set<NODE_T> parentAggregateNodes = groupSet.get(parentNode);
                   
                     parentAggregateNodes.forEach( (parentAggregateNode) -> {
                         aggregateNodeHierarchy.addEdge(aggregateNode, aggregateGroups.get(parentAggregateNode));
                     });
                });
            }
        });
        
        return aggregateNodeHierarchy;
    }
}
