package edu.njit.cs.saboc.blu.core.abn;

import edu.njit.cs.saboc.blu.core.abn.aggregate.AggregateNode;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * A set of utility algorithms for abstraction networks. Defines algorithms that are 
 * applicable to all kinds of abstraction networks.
 * 
 * @author Chris O
 */
public class AbstractionNetworkUtils {

    
    /**
     * Returns details about which singly rooted nodes the parent concepts of a 
     * given singly rooted node's root belong to.
     * 
     * @param <T>
     * @param node
     * @param conceptHierarchy
     * @param allNodes
     * @return 
     */
    public static <T extends SinglyRootedNode> Set<ParentNodeDetails<T>> getSinglyRootedNodeParentNodeDetails(
            T node,
            Hierarchy<Concept> conceptHierarchy,
            Set<T> allNodes) {

        Set<ParentNodeDetails<T>> parentNodeDetails = new HashSet<>();

        Concept root = node.getRoot();

        Set<Concept> parents = conceptHierarchy.getParents(root);

        parents.forEach( (parent) -> {
            
            allNodes.forEach( (otherNode) -> {
                
                if (otherNode.getHierarchy().getNodes().contains(parent)) {
                    parentNodeDetails.add(new ParentNodeDetails<>(parent, otherNode));
                }
                
            });
            
        });

        return parentNodeDetails;
    }

    /**
     * 
     * Returns details about which partition nodes contain the parent concepts
     * of the roots of the given partitioned node.
     * 
     * @param <T>
     * @param node
     * @param hierarchy
     * @param allNodes
     * @return 
     */
    public static <T extends PartitionedNode> Set<ParentNodeDetails<T>> getMultiRootedNodeParentNodeDetails(
            PartitionedNode node,
            Hierarchy<Concept> hierarchy,
            Set<PartitionedNode> allNodes) {

        Set<SinglyRootedNode> internalNodes = node.getInternalNodes();

        Set<ParentNodeDetails<T>> parentNodeDetails = new HashSet<>();

        internalNodes.forEach((internalNode) -> {
            Concept root = internalNode.getRoot();

            Set<Concept> parents = hierarchy.getParents(root);

            parents.forEach((parent) -> {
                allNodes.forEach((otherNode) -> {
                    if (otherNode.getConcepts().contains(parent)) {
                        parentNodeDetails.add(new ParentNodeDetails(parent, otherNode));
                    }
                });
            });
        });

        return parentNodeDetails;
    }

    /**
     * Returns the original hierarchy of nodes from a hierarchy of aggregate nodes.
     * 
     * "Deaggregates" a hierarchy of aggregate nodes
     * 
     * @param <T>
     * @param <V>
     * @param fullNonAggregatedHierarchy
     * @param aggregatedAncestorHierarchy
     * @return 
     */
    public static <T extends SinglyRootedNode, V extends AggregateNode<T>>
            Hierarchy<T> getDeaggregatedAncestorHierarchy(
                    Hierarchy<T> fullNonAggregatedHierarchy,
                    Hierarchy<V> aggregatedAncestorHierarchy) {

        Set<T> allAggregatedNodes = new HashSet<>();

        aggregatedAncestorHierarchy.getNodes().forEach((aggregateNode) -> {
            allAggregatedNodes.addAll(aggregateNode.getAggregatedHierarchy().getNodes());
        });

        Set<T> actualRoots = new HashSet<>();

        aggregatedAncestorHierarchy.getRoots().forEach((root) -> {
            actualRoots.add(root.getAggregatedHierarchy().getRoot());
        });

        //TODO: This can be done with a topological traversal, probably
        Hierarchy<T> potentialHierarchy = fullNonAggregatedHierarchy.getAncestorHierarchy(allAggregatedNodes);

        Hierarchy<T> resultHierarchy = new Hierarchy<>(actualRoots);

        allAggregatedNodes.forEach((node) -> {
            resultHierarchy.addNode(node);
        });

        potentialHierarchy.getEdges().forEach((edge) -> {
            if (allAggregatedNodes.contains(edge.getSource()) && allAggregatedNodes.contains(edge.getTarget())) {
                resultHierarchy.addEdge(edge);
            }
        });

        return resultHierarchy;
    }

    /**
     * Computes the complete subhierarchy of concepts that are summarized by a hierarchy of nodes
     * 
     * @param <T>
     * @param nodeHierarchy
     * @param fullHierarchy
     * @return 
     */
    public static <T extends SinglyRootedNode> Hierarchy<Concept> getConceptHierarchy(
            Hierarchy<T> nodeHierarchy, 
            Hierarchy<Concept> fullHierarchy) {
        
        Set<Concept> roots = new HashSet<>();
        
        nodeHierarchy.getRoots().forEach( (rootNode) -> { 
            roots.add(rootNode.getRoot());
        });
        
        Hierarchy<Concept> conceptHierarchy = new Hierarchy(roots);

        nodeHierarchy.getNodes().forEach( (node) -> {
            conceptHierarchy.addAllHierarchicalRelationships(node.getHierarchy());
        });

        nodeHierarchy.getNodes().forEach((node) -> {
            
            fullHierarchy.getParents(node.getRoot()).forEach((p) -> {
                Concept parent = (Concept) p;

                if (conceptHierarchy.contains(parent)) {
                    conceptHierarchy.addEdge(node.getRoot(), parent);
                }
            });
        });
        
        return conceptHierarchy;
    }
}
