package edu.njit.cs.saboc.blu.core.abn.pareataxonomy;

import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 * A generator class for creating partial-area taxonomies
 * 
 * @author Chris O
 */
public class PAreaTaxonomyGenerator {
    
    protected Set<Concept> identifyPAreaRoots(Hierarchy<Concept> hierarchy,  Map<Concept, Set<InheritableProperty>> conceptRelationships) {
        
        Set<Concept> roots = new HashSet<>();

        for (Concept concept : hierarchy.getNodes()) {
            Set<Concept> parents = hierarchy.getParents(concept);

            Set<InheritableProperty> rels = conceptRelationships.get(concept);

            boolean equalsParent = false;

            for (Concept parent : parents) {
                Set<InheritableProperty> parentRels = conceptRelationships.get(parent);
                
                if (rels.equals(parentRels)) {
                    equalsParent = true;
                    break;
                }
            }

            if (parents.isEmpty() || !equalsParent) {
                roots.add(concept);
            }
        }
        
        return roots;
    }
    
    /**
     * Builds a partial-area taxonomy from the given source hierarchy. 
     * 
     * This process is done by first initializing temporary data structures
     * with the area/parea/parea taxonomy dependencies and then 
     * injecting them into the proper data structures at the end.
     * 
     * @param factory
     * @param sourceHierarchy
     * @return 
     */
    public PAreaTaxonomy derivePAreaTaxonomy(
            final PAreaTaxonomyFactory factory, 
            final Hierarchy<? extends Concept> sourceHierarchy) {
        
        // TODO: This whole process can be replaced by a topological traversal.
        Map<Concept, Set<InheritableProperty>> conceptRelationships = new HashMap<>();
        
        Hierarchy<Concept> hierarchy = (Hierarchy<Concept>)(Hierarchy<?>)sourceHierarchy;

        Set<Concept> concepts = hierarchy.getNodes();
        
        concepts.forEach((concept) -> {
            conceptRelationships.put(concept, factory.getRelationships(concept));
        });
        
        Set<Concept> pareaRoots = identifyPAreaRoots(hierarchy, conceptRelationships);

        Map<Concept, Hierarchy<Concept>> pareaConceptHierarchy = new HashMap<>();
        
        Map<Concept, Set<Concept>> parentPAreaRoots = new HashMap<>();
        Map<Concept, Set<Concept>> childPAreaRoots = new HashMap<>();

        // Step 1: Initialize the partial-area data structures
        pareaRoots.forEach( (root) -> {
            pareaConceptHierarchy.put(root, new Hierarchy<>(root));
            
            parentPAreaRoots.put(root, new HashSet<>());
            childPAreaRoots.put(root, new HashSet<>());
        });

        Map<Concept, HashSet<Concept>> conceptPAreas = new HashMap<>();

        Stack<Concept> stack = new Stack<>();
        
        // Step 2: For all of the roots, add appropriate concepts to the  partial-area. Establish CHILD OF links.
        pareaRoots.forEach( (root) -> {
            stack.add(root);
            
            Set<Concept> processedConcepts = new HashSet<>();
            processedConcepts.add(root);
            
            while (!stack.isEmpty()) {
                Concept concept = stack.pop();
                processedConcepts.add(concept);

                Hierarchy<Concept> pareaHierarchy = pareaConceptHierarchy.get(root);

                if (!conceptPAreas.containsKey(concept)) {
                    conceptPAreas.put(concept, new HashSet<>());
                }

                conceptPAreas.get(concept).add(root);

                Set<Concept> children = hierarchy.getChildren(concept);

                children.forEach( (child) -> {
                    if (pareaRoots.contains(child)) {
                        parentPAreaRoots.get(child).add(root);
                        childPAreaRoots.get(root).add(child);
                    } else {
                        if(conceptRelationships.get(root).equals(conceptRelationships.get(child))) {
                            if(!processedConcepts.contains(child) && !stack.contains(child)) {
                                stack.add(child);
                            }
                            
                            pareaHierarchy.addEdge(child, concept);
                        }
                    }
                    
                });
            }
        });
        
        Set<PArea> pareas = new HashSet<>();
        
        Map<Set<InheritableProperty>, Set<PArea>> pareasByRelationships = new HashMap<>();
        Map<Concept, PArea> pareasByRoot = new HashMap<>();

        PArea rootPArea = null;

        for (Concept root : pareaRoots) {
            PArea parea = new PArea(pareaConceptHierarchy.get(root), conceptRelationships.get(root));

            if (root.equals(sourceHierarchy.getRoot())) {
                rootPArea = parea;
            }
            
            if(!pareasByRelationships.containsKey(parea.getRelationships())) {
                pareasByRelationships.put(parea.getRelationships(), new HashSet<>());
            }
            
            pareasByRoot.put(root, parea);
            pareasByRelationships.get(parea.getRelationships()).add(parea);
            
            pareas.add(parea);
        }
                
        // Step 3: Build the hierarchy of partial-areas
        Hierarchy<PArea> pareaHierarchy = new Hierarchy<>(rootPArea);
        
        pareas.forEach((parea) -> {
            Concept root = parea.getHierarchy().getRoot();

            Set<Concept> parents = hierarchy.getParents(root);

            parents.forEach((parent) -> {
                Set<Concept> parentPAreas = conceptPAreas.get(parent);

                parentPAreas.forEach((parentPAreaRoot) -> {
                    pareaHierarchy.addEdge(parea, pareasByRoot.get(parentPAreaRoot));
                });
            });
        });
        
        // Step 4: Create the areas from the sets of partial-areas with the 
        // same types of inheritable properties
        Set<Area> areas = new HashSet<>();
        
        Area rootArea = null;
        
        Map<Set<InheritableProperty>, Area> areasByRelationships = new HashMap<>();
        
        for(Entry<Set<InheritableProperty>, Set<PArea>> entry : pareasByRelationships.entrySet()) {
            Area area = new Area(entry.getValue(), entry.getKey());
            
            areas.add(area);
            
            if(area.getPAreas().contains(rootPArea)) {
                rootArea = area;
            }
            
            areasByRelationships.put(area.getRelationships(), area);
        }
        
        // Step 5: Build the area hierarchy
        Hierarchy<Area> areaHierarchy = new Hierarchy<>(rootArea);
        
        areas.forEach((area) -> {
            if (!area.equals(areaHierarchy.getRoot())) {
                
                Set<PArea> areaPAreas = area.getPAreas();
                
                areaPAreas.forEach((parea) -> {
                    Area parentArea = areasByRelationships.get(parea.getRelationships());
                    areaHierarchy.addEdge(area, parentArea);
                });
            }
        });
        
        AreaTaxonomy areaTaxonomy = factory.createAreaTaxonomy(areaHierarchy, hierarchy);
        PAreaTaxonomy pareaTaxonomy = factory.createPAreaTaxonomy(areaTaxonomy, pareaHierarchy, hierarchy);

        return pareaTaxonomy;
    }
    
    /**
     * Creates a partial-area taxonomy from a hierarchy of partial-areas
     * 
     * @param <T>
     * @param factory
     * @param pareaHierarchy
     * @param sourceHierarchy
     * @return 
     */
    public <T extends PArea> PAreaTaxonomy<T> createTaxonomyFromPAreas(
            PAreaTaxonomyFactory factory, 
            Hierarchy<T> pareaHierarchy,
            Hierarchy<Concept> sourceHierarchy) {
                     
        HashMap<Set<InheritableProperty>, Set<T>> pareasByRelationships = new HashMap<>();
        
        Hierarchy<Concept> conceptHierarchy = new Hierarchy<>(pareaHierarchy.getRoot().getRoot());
                
        pareaHierarchy.getNodes().forEach( (parea) -> {
            Set<InheritableProperty> properties = parea.getRelationships();
            
            if(!pareasByRelationships.containsKey(properties)) {
                pareasByRelationships.put(properties, new HashSet<>());
            }
            
            pareasByRelationships.get(properties).add(parea);
            
            conceptHierarchy.addAllHierarchicalRelationships(parea.getHierarchy());
        });
        
        pareaHierarchy.getNodes().forEach( (parea) -> {
            Concept root = parea.getRoot();
            
            sourceHierarchy.getParents(root).forEach( (parent) -> {
               if(conceptHierarchy.contains(parent)) {
                   conceptHierarchy.addEdge(root, parent);
               } 
            });
        });
        
        HashMap<Set<InheritableProperty>, Area> areasByRelationships = new HashMap<>();
        
        Area rootArea = null;
        
        for (Entry<Set<InheritableProperty>, Set<T>> entry : pareasByRelationships.entrySet()) {
            Set<PArea> pareas = (Set<PArea>)entry.getValue();
            
            Area area = new Area(pareas, entry.getKey());

            if (area.getPAreas().contains(pareaHierarchy.getRoot())) {
                rootArea = area;
            }

            areasByRelationships.put(area.getRelationships(), area);
        }
        
        Hierarchy<Area> areaHierarchy = new Hierarchy<>(rootArea);
 
        areasByRelationships.values().forEach((area) -> {
            if (!area.equals(areaHierarchy.getRoot())) {
                Set<PArea> areaPAreas = area.getPAreas();

                areaPAreas.forEach((parea) -> {
                    Area parentArea = areasByRelationships.get(parea.getRelationships());
                    areaHierarchy.addEdge(area, parentArea);
                });
            }
        });
        
        AreaTaxonomy areaTaxonomy = factory.createAreaTaxonomy(areaHierarchy, conceptHierarchy);
        PAreaTaxonomy<T> pareaTaxonomy = factory.createPAreaTaxonomy(areaTaxonomy, pareaHierarchy, conceptHierarchy);
   
        return pareaTaxonomy;
    }
}
