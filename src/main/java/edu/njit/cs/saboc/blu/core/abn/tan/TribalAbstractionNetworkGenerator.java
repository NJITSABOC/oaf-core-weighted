package edu.njit.cs.saboc.blu.core.abn.tan;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Generator for creating tribal abstraction networks from hierarchies of concepts
 * 
 * @author Chris O
 */
public class TribalAbstractionNetworkGenerator {
        
    /**
     * Creates a TAN from a singly rooted hierarchy of concepts. The 
     * children of the root are used as the patriarchs
     * 
     * @param sourceHierarchy
     * @param factory
     * @return 
     */
    public ClusterTribalAbstractionNetwork deriveTANFromSingleRootedHierarchy(
            Hierarchy<? extends Concept> sourceHierarchy,
            TANFactory factory) {
        
        Hierarchy<Concept> hierarchy = (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy;
        
        hierarchy = new Hierarchy<>(
                hierarchy.getChildren(sourceHierarchy.getRoot()), 
                hierarchy);
        
        return deriveTANFromMultiRootedHierarchy(hierarchy, factory);
    }

    /**
     * Creates a TAN from a multi-rooted hierarchy of concepts. The roots of the hierarch are 
     * used as the patriarchs.
     * 
     * @param sourceHierarchy
     * @param factory
     * @return 
     */
    public ClusterTribalAbstractionNetwork deriveTANFromMultiRootedHierarchy(
            Hierarchy<? extends Concept> sourceHierarchy,
            TANFactory factory) {
        
        // TODO: Combine all of this into a single topological traversal using
        // a visitor for the hierarchy below.
        
        Hierarchy<Concept> hierarchy = (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy;

        Set<Concept> patriarchs = hierarchy.getRoots();

        // The set of tribes a given concept belongs to
        HashMap<Concept, Set<Concept>> conceptTribes = new HashMap<>();

        HashMap<Concept, Integer> remainingParentCount = new HashMap<>();

        Stack<Concept> pendingConcepts = new Stack<>();

        pendingConcepts.addAll(patriarchs);

        sourceHierarchy.getNodes().forEach((c) -> {
            if (patriarchs.contains(c)) {
                remainingParentCount.put(c, 0);
            } else {
                remainingParentCount.put(c, hierarchy.getParents(c).size());
            }
        });

        // Step 1: Perform a topological traversal to identify which 
        // tribes a given concept belongs to
        while (!pendingConcepts.isEmpty()) {
            Concept c = pendingConcepts.pop();

            if (patriarchs.contains(c)) {
                conceptTribes.put(c, new HashSet<>(Arrays.asList(c)));
            } else {
                Set<Concept> parents = hierarchy.getParents(c);

                Set<Concept> tribalSet = new HashSet<>();

                parents.forEach((parent) -> {
                    tribalSet.addAll(conceptTribes.get(parent));
                });

                conceptTribes.put(c, tribalSet);
            }

            Set<Concept> children = hierarchy.getChildren(c);

            children.forEach((child) -> {
                int parentCount = remainingParentCount.get(child) - 1;

                if (parentCount == 0) {
                    pendingConcepts.push(child);
                } else {
                    remainingParentCount.put(child, parentCount);
                }
            });
        }

        // The set of cluster root concepts in the hierarchy
        HashSet<Concept> roots = new HashSet<>();
        roots.addAll(patriarchs);

        // Step 2: Based on the tribes a given concept's parent(s) belong to, 
        // determine if the concept is a root
        for (Map.Entry<Concept, Set<Concept>> entry : conceptTribes.entrySet()) {
            if (roots.contains(entry.getKey())) {
                continue;
            }

            Set<Concept> myCluster = entry.getValue(); // The patriarchs that have this concept as a descendent

            Set<Concept> parents = hierarchy.getParents(entry.getKey()); // Get parents of this concept

            boolean isRoot = true;

            for (Concept parent : parents) { // For each parent
                Set<Concept> parentTribes = conceptTribes.get(parent);

                if (parentTribes.equals(myCluster)) { // If a parent has the same group of tribes
                    isRoot = false; // Then this is not a root concept
                    break;
                }
            }

            if (isRoot) { // If concept is a root
                roots.add(entry.getKey()); // Add it to roots
            }
        }

        // Stores the hierarchy of concepts summarized by each cluster
        final HashMap<Concept, Hierarchy<Concept>> clusterConceptHierarchy = new HashMap<>();

        // Stores the list of clusters each concept belongs to
        final HashMap<Concept, Set<Concept>> conceptClusters = new HashMap<>();


        // Step 3: Establish the hierarchy of concepts in each cluster
        
        for (Concept root : roots) { // For each root
            clusterConceptHierarchy.put(root, new Hierarchy<>(root)); // Create a new cluster

            Set<Concept> rootTribalSet = conceptTribes.get(root);

            Stack<Concept> stack = new Stack<>();

            stack.add(root);

            while (!stack.isEmpty()) { // Traverse down DAG and add concepts to cluster
                Concept concept = stack.pop();

                Set<Concept> conceptTribalSet = conceptTribes.get(concept);

                if (rootTribalSet.equals(conceptTribalSet)) {
                    if (!conceptClusters.containsKey(concept)) {
                        conceptClusters.put(concept, new HashSet<>());
                    }

                    conceptClusters.get(concept).add(root); // Set concept as belonging to current header

                    Set<Concept> children = hierarchy.getChildren(concept);

                    for (Concept child : children) { // Process all children
                        if (stack.contains(child) || roots.contains(child) || !conceptTribes.get(child).equals(rootTribalSet)) {
                            continue;
                        }

                        clusterConceptHierarchy.get(root).addEdge(child, concept);

                        stack.add(child);
                    }
                }
            }
        }

        Set<Cluster> patriarchClusters = new HashSet<>();
        
        // Create Cluster objects
        
        Map<Set<Concept>, Set<Cluster>> clustersByPatriarchs = new HashMap<>();
        Map<Concept, Cluster> clustersByRoot = new HashMap<>();

        roots.forEach((root) -> {
            Set<Concept> rootTribes = conceptTribes.get(root);

            Cluster cluster = new Cluster(clusterConceptHierarchy.get(root), rootTribes);
            
            if(rootTribes.size() == 1) {
                patriarchClusters.add(cluster);
            }
            
            if(!clustersByPatriarchs.containsKey(rootTribes)) {
                clustersByPatriarchs.put(rootTribes, new HashSet<>());
            }
            
            clustersByPatriarchs.get(rootTribes).add(cluster);
            clustersByRoot.put(root, cluster);
        });
        
        // Step 4: Build the hierarchy of clusters
        
        Hierarchy<Cluster> clusterHierarchy = new Hierarchy<>(patriarchClusters);
        
        clustersByRoot.values().forEach( (cluster) -> {
            if (!clusterHierarchy.getRoots().contains(cluster)) {
                Set<Concept> rootParents = hierarchy.getParents(cluster.getRoot());
                
                rootParents.forEach( (parent) -> {
                    
                   Set<Concept> parentClusterRoots = conceptClusters.get(parent);
                   
                   parentClusterRoots.forEach( (clusterRoot) -> {
                       Cluster parentCluster = clustersByRoot.get(clusterRoot);
                       clusterHierarchy.addEdge(cluster, parentCluster);
                   });
                 
                });
            }
        });

 
        // Step 5: initialize the bands and build the hierarchy of Bands
        // for the Band TAN
        Map<Set<Concept>, Band> bandsByPatriarchs = new HashMap<>();
        
        Set<Band> patriarchBands = new HashSet<>();
        
        clustersByPatriarchs.forEach( (bandPatriarchs, clusters) -> {
            Band band = new Band(clusters, bandPatriarchs);
            
            bandsByPatriarchs.put(band.getPatriarchs(), band);
            
            if(band.getPatriarchs().size() == 1) {
                patriarchBands.add(band);
            }
        });
    
        Hierarchy<Band> bandHierarchy = new Hierarchy<>(patriarchBands);
        
        bandsByPatriarchs.values().forEach( (band) -> {
            if(!bandHierarchy.getRoots().contains(band)) {
                band.getClusters().forEach( (cluster) -> {
                   Set<Cluster> parentClusters = clusterHierarchy.getParents(cluster);
                   
                   parentClusters.forEach( (parentCluster) -> {
                       Band parentBand = bandsByPatriarchs.get(parentCluster.getPatriarchs());
                       
                       bandHierarchy.addEdge(band, parentBand);
                   });
                });
            }
        });
        
        BandTribalAbstractionNetwork bandTAN = factory.createBandTAN(bandHierarchy, hierarchy);
        ClusterTribalAbstractionNetwork tan = factory.createClusterTAN(bandTAN, clusterHierarchy, hierarchy);

        return tan;
    }
    
    /**
     * Creates a TAN from a hierarchy of clusters
     * 
     * @param <T>
     * @param clusterHierarchy
     * @param sourceHierarchy
     * @param factory
     * @return 
     */
    public <T extends Cluster> ClusterTribalAbstractionNetwork<T> createTANFromClusters(
            Hierarchy<T> clusterHierarchy,
            Hierarchy<Concept> sourceHierarchy,
            TANFactory factory) {
                
        // For now assuming only one cluster is picked as a root
        Hierarchy<Concept> conceptHierarchy = new Hierarchy<>(clusterHierarchy.getRoot().getRoot());
        
        Map<Set<Concept>, Set<Cluster>> clusterBands = new HashMap<>();
        
        clusterHierarchy.getNodes().forEach((cluster) -> {
            conceptHierarchy.addAllHierarchicalRelationships(cluster.getHierarchy());
            
            if(!clusterBands.containsKey(cluster.getPatriarchs())) {
                clusterBands.put(cluster.getPatriarchs(), new HashSet<>());
            }
            
            clusterBands.get(cluster.getPatriarchs()).add(cluster);
        });
        
        clusterHierarchy.getNodes().forEach( (cluster) -> {
            Concept root = cluster.getRoot();
            
            sourceHierarchy.getParents(root).forEach( (parent) -> {
               if(conceptHierarchy.contains(parent)) {
                   conceptHierarchy.addEdge(root, parent);
               } 
            });
        });

        Map<Set<Concept>, Band> bandsByPatriarchs = new HashMap<>();
        
        clusterBands.forEach( (patriarchs, clusters) -> {
            Band band = new Band(clusters, patriarchs);
            bandsByPatriarchs.put(patriarchs, band);
        });
        
        Set<Band> rootBands = new HashSet<>();
        
        clusterHierarchy.getRoots().forEach((cluster) -> {
            rootBands.add(bandsByPatriarchs.get(cluster.getPatriarchs()));
        });
        
        Hierarchy<Band> bandHierarchy = new Hierarchy<>(rootBands);
        
        bandsByPatriarchs.values().forEach((band) -> {
            
            band.getClusters().forEach( (cluster) -> {
                
                Set<T> parentClusters = clusterHierarchy.getParents((T)cluster);
                
                parentClusters.forEach((parentCluster) -> {
                    bandHierarchy.addEdge(band, bandsByPatriarchs.get(parentCluster.getPatriarchs()));
                });
            });
        });
        
        
        BandTribalAbstractionNetwork bandTAN = factory.createBandTAN(bandHierarchy, conceptHierarchy);
        ClusterTribalAbstractionNetwork<T> tan = factory.createClusterTAN(bandTAN, clusterHierarchy, conceptHierarchy);
        
        return tan;
    }
    
    /**
     * Creates a TAN from the hierarchy of concepts summarized by a singly rooted node. 
     * Uses the children of the root of the node as patriarchs.
     * 
     * @param <T>
     * @param <V>
     * @param <U>
     * @param sourceAbN
     * @param sourceNode
     * @param factory
     * @return 
     */
    public <T extends Cluster, V extends SinglyRootedNode, U extends AbstractionNetwork<V>> ClusterTribalAbstractionNetwork<T> createTANFromSinglyRootedNode(
            U sourceAbN,
            V sourceNode,
            TANFactory factory) {
        
        ClusterTribalAbstractionNetwork<T> theTAN = this.deriveTANFromSingleRootedHierarchy(sourceNode.getHierarchy(), factory);
        
        return factory.createTANFromSinglyRootedNode(theTAN, sourceNode, sourceAbN);
    }
    
    /**
     * Creates a TAN from a partitioned node (e.g., an area or another band)
     * 
     * @param <T>
     * @param <V>
     * @param <U>
     * @param sourceAbN
     * @param sourceNode
     * @param factory
     * @return 
     */
    public <T extends Cluster, V extends PartitionedNode, U extends PartitionedAbstractionNetwork> ClusterTribalAbstractionNetwork<T> createTANFromPartitionedNode(
            U sourceAbN,
            V sourceNode,
            TANFactory factory) {
        
        // TODO: Make it more general for multirooted nodes instead? Maybe not.
        
        ClusterTribalAbstractionNetwork<T> theTAN = this.deriveTANFromMultiRootedHierarchy(sourceNode.getHierarchy(), factory);
        
        return factory.createTANFromPartitionedNode(theTAN, sourceNode, sourceAbN);
    }
}
