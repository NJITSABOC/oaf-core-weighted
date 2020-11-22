package edu.njit.cs.saboc.blu.core.abn.disjoint;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.provenance.SimpleDisjointAbNDerivation;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * Creates a disjoint abstraction network (composed of disjoint nodes). Each concept in the 
 * summarized subhierarchy is summarized by exactly one disjoint node.
 * 
 * Creates disjoint nodes by identifying intersecting subhierarchies of concepts, 
 * where the subhierarchies are defined by the singly rooted nodes of an abstraction newtork.
 * 
 * The concepts that exist a point of intersection will be summarized by a disjoint node.
 * 
 * @author Chris O
 * 
 * @param <PARENTABN_T> The type of abstraction network this disjoint abstraction 
 * network was created from (e.g., partial-area taxonomy for a disjoint partial-area taxonomy)
 * 
 * @param <PARENTNODE_T> The type of node in the parent abstraction network that 
 * is being separated into disjoint units
 */
public class DisjointAbNGenerator<
        PARENTABN_T extends AbstractionNetwork<PARENTNODE_T>,
        PARENTNODE_T extends SinglyRootedNode> {
    
    /**
     * Generates a disjoint abstraction network from the given nodes.
     * 
     * This code is based on the original disjoint partial-area program 
     * developed in ~2009.
     * 
     * @param factory
     * 
     * @param parentAbN The (non-disjoint) abstract network
     * 
     * @param parentNodes The set of nodes that will be turned into disjoint nodes

     * @return 
     */
    public DisjointAbstractionNetwork generateDisjointAbstractionNetwork(
            DisjointAbNFactory<PARENTNODE_T, ? extends DisjointNode<PARENTNODE_T>> factory,
            PARENTABN_T parentAbN, 
            Set<PARENTNODE_T> parentNodes) {
        
        // Step 1: Identify the root concepts and build the subhierachy of concepts,
        // from the set of all singly rooted nodes, that will be summarized by a
        // disjoint abstraction network
        
        Set<Concept> originalRoots = new HashSet<>();
        
        parentNodes.forEach( (node) -> {
            originalRoots.add(node.getRoot());
        });
        
        Hierarchy<Concept> conceptHierarchy = new Hierarchy<>(originalRoots);
        
        parentNodes.forEach( (node) -> {
            conceptHierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });
    
        // A mapping of concepts to the group(s) they belong to.
        Map<Concept, Set<PARENTNODE_T>> conceptGroupMap = new HashMap<>();
        
        Set<PARENTNODE_T> identifiedOverlappingGroups = new HashSet<>();
        
        // Step 2: DFS to identify concepts that are summarized by multiple nodes, 
        // along with which node(s) each concept is summarized by
        parentNodes.forEach((node) -> {
            Concept root = node.getRoot();

            Stack<Concept> stack = new Stack<>();
            stack.push(root);

            Set<Concept> processedConcepts = new HashSet<>();

            // Traverse this partial-area's hierarchy, mark which concepts belong to the partial-area
            while (!stack.isEmpty()) {
                Concept c = stack.pop();

                if (!conceptGroupMap.containsKey(c)) {
                    conceptGroupMap.put(c, new HashSet<>());
                }

                conceptGroupMap.get(c).add(node);
                
                if(conceptGroupMap.get(c).size() > 1) {
                    identifiedOverlappingGroups.addAll(conceptGroupMap.get(c));
                }

                processedConcepts.add(c);

                Set<Concept> children = conceptHierarchy.getChildren(c);

                for (Concept child : children) {
                    if (!stack.contains(child) && !processedConcepts.contains(child)) {
                        stack.push(child);
                    }
                }
            }
        });
        
        int maxOverlap = 0;

        for (Map.Entry<Concept, Set<PARENTNODE_T>> entry : conceptGroupMap.entrySet()) {
            if (entry.getValue().size() > maxOverlap) {
                maxOverlap = entry.getValue().size();
            }
        }

        // Step 3: Identify the roots of the disjoint partial-area taxonomy
        Set<Concept> allArticulationPoints = new HashSet<>();
        
        for (int c = 2; c <= maxOverlap; c++) {
            HashMap<Concept, Set<PARENTNODE_T>> overlappingConcepts = new HashMap<>();

            for (Map.Entry<Concept, Set<PARENTNODE_T>> entry : conceptGroupMap.entrySet()) {
                if (entry.getValue().size() == c) {
                    overlappingConcepts.put(entry.getKey(), entry.getValue());
                }
            }

            Set<Concept> conceptSet = new HashSet<>(overlappingConcepts.keySet());

            Set<Concept> copyConceptSet;

            Set<Concept> articulationPoints = new HashSet<>();

            for (Concept concept : overlappingConcepts.keySet()) {
                Set<Concept> parents = conceptHierarchy.getParents(concept);

                copyConceptSet = new HashSet<>(conceptSet);
                copyConceptSet.retainAll(parents);

                if (copyConceptSet.isEmpty()) {
                    articulationPoints.add(concept);
                } else {
                    continue;
                }

                allArticulationPoints.addAll(articulationPoints);
            }
        }
        
        Set<Concept> allRoots = new HashSet<>();

        for (Concept root : allArticulationPoints) {
            Set<PARENTNODE_T> conceptGroups = conceptGroupMap.get(root);

            conceptGroups.forEach((group) -> {
                allRoots.add(group.getRoot());
            });
        }

        allRoots.addAll(allArticulationPoints);
        
        HashMap<Concept, Integer> parentCounts = new HashMap<>();
        
        for(Concept node : conceptHierarchy.getNodes()) {
            
            if(allArticulationPoints.contains(node)) {
                parentCounts.put(node, 0);
            } else {
                parentCounts.put(node, conceptHierarchy.getParents(node).size());
            }
        }
        
        Queue<Concept> queue = new LinkedList<>();
        queue.addAll(allRoots);
        
        Map<Concept, Set<Concept>> reachableFrom = new HashMap<>();
        
        
        // Step 4: Do a topological traversal to identify which concepts belong to which disjoint nodes
        while(!queue.isEmpty()) {
            Concept node = queue.remove();
            
            Set<Concept> conceptRoots = new HashSet<>();
            
            Set<Concept> parents = conceptHierarchy.getParents(node);
            
            if (allArticulationPoints.contains(node)) {
                conceptRoots.add(node);
            } else {
                for (Concept parent : parents) {
                    conceptRoots.addAll(reachableFrom.get(parent));
                }
                
                for(Concept parent : parents) {
                    if(!conceptRoots.equals(reachableFrom.get(parent))) {
                        conceptRoots.clear();
                        conceptRoots.add(node);
                        
                        allRoots.add(node);
                        
                        break;
                    }
                }
            }
            
            reachableFrom.put(node, conceptRoots);

            Set<Concept> children = conceptHierarchy.getChildren(node);
                       
            for(Concept child : children) {
                int childParentCount = parentCounts.get(child);
                
                if(childParentCount - 1 == 0) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, childParentCount - 1);
                }
            }
        }
        
        Set<Concept> roots = new HashSet<>();
        
        // Step 5: Initialize disjoint nodes and the disjoint node hierarchy
        HashMap<Concept, Hierarchy<Concept>> disjointGroupConceptHierarchy = new HashMap<>();
        HashMap<Concept, Set<Concept>> disjointGroupParents = new HashMap<>();
        
        HashMap<Concept, Concept> conceptDisjointGroup = new HashMap<>();
        
        for (PARENTNODE_T group : identifiedOverlappingGroups) {
            Concept root = group.getRoot();
            
            roots.add(root);
            
            disjointGroupConceptHierarchy.put(root, new Hierarchy<>(root));

            disjointGroupParents.put(root, new HashSet<>());
        }

        for (Concept root : allRoots) {
            if (!roots.contains(root)) {
                
                roots.add(root);

                disjointGroupConceptHierarchy.put(root,  new Hierarchy<>(root));

                disjointGroupParents.put(root, new HashSet<>());
            }
        }

        // Step 6: Add all concepts to their respective disjoint partial areas
        roots.forEach((root) -> {
            Stack<Concept> stack = new Stack<>();
            stack.add(root);

            Set<Concept> processedConcepts = new HashSet<>();
            
            while (!stack.isEmpty()) {                
                Concept concept = stack.pop();
                
                conceptDisjointGroup.put(concept, root);

                processedConcepts.add(concept);

                Set<Concept> children = conceptHierarchy.getChildren(concept);

                children.forEach((child) -> {
                    if (allRoots.contains(child)) {
                        disjointGroupParents.get(child).add(root);
                    } else {
                        if (!stack.contains(child) && !processedConcepts.contains(child)) {
                            stack.add(child);
                            
                            disjointGroupConceptHierarchy.get(root).addEdge(child, concept);
                        }
                    }
                });
            }
        });
        
        // Step 7: Build the disjoint node hierarchy
        HashMap<Concept, DisjointNode<PARENTNODE_T>> disjointGroups = new HashMap<>();
        
        Set<DisjointNode<PARENTNODE_T>> rootGroups = new HashSet<>();
        
        roots.forEach((disjointNodeRoot) -> {
            Hierarchy<Concept> nodeConceptHierarchy = disjointGroupConceptHierarchy.get(disjointNodeRoot);
            Set<PARENTNODE_T> overlapsIn = conceptGroupMap.get(disjointNodeRoot);
            
            DisjointNode<PARENTNODE_T> rootGroup = factory.createDisjointNode(nodeConceptHierarchy, overlapsIn);

            disjointGroups.put(disjointNodeRoot, rootGroup);
            
            if (originalRoots.contains(disjointNodeRoot)) {
                rootGroups.add(rootGroup);
            }
        });
        
        Hierarchy<DisjointNode<PARENTNODE_T>> groupHierarchy = new Hierarchy<>(rootGroups);
        
        disjointGroups.values().forEach( (disjointNode) -> {
            
            if (!groupHierarchy.getRoots().contains(disjointNode)) {

                Set<Concept> pareaDisjointGroupRoots = disjointGroupParents.get(disjointNode.getRoot());

                pareaDisjointGroupRoots.forEach( (parentNodeRoot) -> {
                    groupHierarchy.addEdge(disjointNode, disjointGroups.get(parentNodeRoot));
                });
            }
        });
        
        Set<PARENTNODE_T> overlappingNodes = new HashSet<>();
        
        groupHierarchy.getNodes().forEach( (disjointNode) -> {
            if(disjointNode.getOverlaps().size() > 1) {
                overlappingNodes.addAll(disjointNode.getOverlaps());
            }
        });

        return new DisjointAbstractionNetwork(
                parentAbN, 
                groupHierarchy, 
                conceptHierarchy, 
                factory,
                maxOverlap, 
                parentNodes, 
                overlappingNodes, 
                new SimpleDisjointAbNDerivation(factory, parentAbN.getDerivation(), conceptHierarchy.getRoots()));
    }
}
