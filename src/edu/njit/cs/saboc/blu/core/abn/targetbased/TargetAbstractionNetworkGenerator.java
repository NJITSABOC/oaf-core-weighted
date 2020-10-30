package edu.njit.cs.saboc.blu.core.abn.targetbased;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetworkUtils;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.targetbased.provenance.TargetAbNDerivation;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * A generator class for creating target abstraction networks
 * 
 * @author Chris O
 */
public class TargetAbstractionNetworkGenerator {
    
    /**
     * Creates a target abstraction network that summarizes the subhierarchy of
     * target concepts that have incoming relationships of a specified type 
     * coming from a source subhierarchy. 
     * 
     * @param factory
     * @param sourceHierarchy
     * @param relationshipType
     * @param targetHierarchy
     * @return 
     */
    public TargetAbstractionNetwork deriveTargetAbstractionNetwork(
            TargetAbstractionNetworkFactory factory,
            Hierarchy<Concept> sourceHierarchy, 
            InheritableProperty relationshipType, 
            Hierarchy<Concept> targetHierarchy) {
        
        return this.deriveTargetAbstractionNetwork(
                factory, 
                sourceHierarchy, 
                Collections.singleton(relationshipType), 
                targetHierarchy);
    }
    
    public TargetAbstractionNetworkFromPArea deriveTargetAbNFromPArea(
            TargetAbstractionNetworkFactory factory,
            PAreaTaxonomy sourceTaxonomy,
            PArea parea,
            InheritableProperty relationshipType,
            Hierarchy<Concept> targetHierarchy) {
        
        TargetAbstractionNetwork targetAbN = this.deriveTargetAbstractionNetwork(
                factory, 
                parea.getHierarchy(), 
                relationshipType, 
                targetHierarchy);
        
        return factory.createTargetAbNFromPArea(targetAbN, sourceTaxonomy, parea);
    }
    
    /**
     * Creates a target abstraction network that summarizes the subhierarchy of
     * target concepts that have incoming relationships of specified types
     * coming from a specified source subhierarchy. 
     * 
     * @param factory
     * @param sourceHierarchy
     * @param relationshipTypes
     * @param completeTargetHierarchy
     * @return 
     */
    public TargetAbstractionNetwork deriveTargetAbstractionNetwork(
            TargetAbstractionNetworkFactory factory,
            Hierarchy<Concept> sourceHierarchy, 
            Set<InheritableProperty> relationshipTypes, 
            Hierarchy<Concept> completeTargetHierarchy) {
        
        Map<Concept, Set<RelationshipTriple>> relationshipsToTargetHierarchy = new HashMap<>();
        
        Map<Concept, Set<RelationshipTriple>> relationshipsFromSourceHierarchy = new HashMap<>();
        
        Set<Concept> uniqueTargets = new HashSet<>();
        
        // Step 1: Identify the targets of the relationships
        sourceHierarchy.getNodes().forEach((concept) -> {
            Set<RelationshipTriple> relationships = factory.getRelationshipsToTargetHierarchyFor(
                    concept, 
                    relationshipTypes, 
                    completeTargetHierarchy);
            
            relationshipsToTargetHierarchy.put(concept, relationships);
            
            relationships.forEach( (rel) -> {
                uniqueTargets.add(rel.getTarget());
                
                if(!relationshipsFromSourceHierarchy.containsKey(rel.getTarget())) {
                    relationshipsFromSourceHierarchy.put(rel.getTarget(), new HashSet<>());
                }
                
                relationshipsFromSourceHierarchy.get(rel.getTarget()).add(rel);
            });
        });
        
        Hierarchy<Concept> utilizedTargetHierarchy = completeTargetHierarchy.getAncestorHierarchy(uniqueTargets);

        Map<Concept, Set<Concept>> lowestNontargetAncestors = getLowestNonTargetAncestor(uniqueTargets, utilizedTargetHierarchy);
        
        Set<Concept> targetGroupRoots = new HashSet<>(Collections.singleton(utilizedTargetHierarchy.getRoot()));
        
        // Step 2: Identify the roots of the target groups (these are the 
        // lowest non-target ancestors of the target concepts).
        lowestNontargetAncestors.values().forEach((lowestNontargetConcepts) -> {
            targetGroupRoots.addAll(lowestNontargetConcepts);
        });
       
        // The target groups a concept belongs to
        Map<Concept, Set<Concept>> conceptsGroups = new HashMap<>();

        // The subhierarchy of (all!) concepts summarized by a target group, includes non-targets
        Map<Concept, Hierarchy<Concept>> conceptsInGroup = new HashMap<>();
        
        Map<Concept, Integer> parentCounts = new HashMap<>();

        Map<Concept, Set<RelationshipTriple>> groupIncomingRelationships = new HashMap<>();
        
        for (Concept concept : utilizedTargetHierarchy.getNodes()) {
            conceptsGroups.put(concept, new HashSet<>());
            
            parentCounts.put(concept, utilizedTargetHierarchy.getParents(concept).size());
            
            groupIncomingRelationships.put(concept, new HashSet<>());

            if (targetGroupRoots.contains(concept)) {
                conceptsGroups.get(concept).add(concept);

                conceptsInGroup.put(concept, new Hierarchy<>(concept));
            }
        }

        Queue<Concept> queue = new LinkedList<>();
        
        queue.add(utilizedTargetHierarchy.getRoot()); // Start from the root of the hierarchy...

        // Step 3: Identify the hierarchy of concepts that belong to each 
        // target group
        while (!queue.isEmpty()) {
            Concept concept = queue.remove();

            Set<Concept> parents = utilizedTargetHierarchy.getParents(concept);
            
            // Add concepts to the target group. These may be targets or not.
            if (!targetGroupRoots.contains(concept)) {
                parents.forEach((parent) -> {
                    Set<Concept> parentGroups = conceptsGroups.get(parent);

                    conceptsGroups.get(concept).addAll(parentGroups);

                    parentGroups.forEach((parentGroupRoot) -> {
                        conceptsInGroup.get(parentGroupRoot).addEdge(concept, parent);
                    });
                });
            }
            
            // Add any incoming relationships
            conceptsGroups.get(concept).forEach( (root) -> {
                groupIncomingRelationships.get(root).addAll(
                        relationshipsFromSourceHierarchy.getOrDefault(concept, Collections.emptySet()));
            });

            Set<Concept> children = utilizedTargetHierarchy.getChildren(concept);

            children.forEach((child) -> {
                int childParentCount = parentCounts.get(child) - 1;

                if (childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCounts.put(child, childParentCount);
                }
            });
        }
        
        Map<Concept, TargetGroup> targetGroups = new HashMap<>();
        
        // Create the target groups...
        targetGroupRoots.forEach((root) -> {
            targetGroups.put(root, 
                    new TargetGroup(conceptsInGroup.get(root), 
                    new IncomingRelationshipDetails(groupIncomingRelationships.get(root))));
        });
        
        // Step 4: Build the target group hierarchy
        Hierarchy<TargetGroup> nodeHierarchy = new Hierarchy<>(targetGroups.get(utilizedTargetHierarchy.getRoot()));
        
        targetGroups.values().forEach((group) -> {
            Concept root = group.getRoot();
            
            Set<Concept> parents = utilizedTargetHierarchy.getParents(root);
            
            parents.forEach( (parent) -> {
                Set<Concept> parentGroupRoots = conceptsGroups.get(parent);
                
                parentGroupRoots.forEach( (parentGroupRoot) -> {
                    nodeHierarchy.addEdge(group, targetGroups.get(parentGroupRoot));
                });
            });
        });
        
        TargetAbNDerivation derivation = new TargetAbNDerivation(
                        factory, 
                        sourceHierarchy.getRoot(), 
                        relationshipTypes.iterator().next(), 
                        utilizedTargetHierarchy.getRoot());
        
        return factory.createTargetAbstractionNetwork(
                nodeHierarchy, 
                sourceHierarchy, 
                derivation);
    }
    
    public <T extends TargetGroup> TargetAbstractionNetwork createTargetAbNFromTargetGroups(
            TargetAbstractionNetworkFactory factory,
            Hierarchy<T> hierarchy,
            Hierarchy<Concept> fullSourceHierarchy) {
        
        Hierarchy<Concept> sourceHierarchy = AbstractionNetworkUtils.getConceptHierarchy(hierarchy, fullSourceHierarchy);
        
        return new TargetAbstractionNetwork(factory, hierarchy, sourceHierarchy, null);
    }
    
    
    /**
     * Identifies the lowest common ancestor(s) of a set of concepts that are not targets
     * of a type of relationship. Used to define the roots of the nodes in 
     * the target abstraction network.
     * 
     * TODO: Replace with visitor
     * 
     * @param targets
     * @param hierarchy
     * @return 
     */
    private Map<Concept, Set<Concept>> getLowestNonTargetAncestor(Set<Concept> targets, Hierarchy<Concept> hierarchy) {
        
        Map<Concept, Set<Concept>> lowestNontargetConcepts = new HashMap<>();
        
        targets.forEach((target) -> {
            Set<Concept> lowestAncestorsWithoutIncoming = new HashSet<>();
            
            Stack<Concept> stack = new Stack<>();
            
            stack.addAll(hierarchy.getParents(target));
            
            while(!stack.isEmpty()) {
                Concept concept = stack.pop();
                
                if(targets.contains(concept)) {
                    for(Concept parent : hierarchy.getParents(concept)) {
                        if(!stack.contains(parent) && !targets.contains(parent)) {
                            stack.add(parent);
                        }
                    }
                } else {
                    lowestAncestorsWithoutIncoming.add(concept);
                }
            }
            
            lowestNontargetConcepts.put(target, lowestAncestorsWithoutIncoming);
        });
        
        return lowestNontargetConcepts;
    }
}
