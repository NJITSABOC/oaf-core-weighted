package edu.njit.cs.saboc.blu.core.gui.dialogs.concepthierarchy;

import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.datastructure.hierarchy.Hierarchy;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chris
 */
public class NodeConceptHierarchyLoader implements Runnable {

    private final SinglyRootedNode node;
    
    private final NodeConceptHierarchicalViewPanel hierarchyViewPanel;
    
    private final HashMap<Concept, ConceptEntry> conceptEntryMap = new HashMap<>();

    public NodeConceptHierarchyLoader(SinglyRootedNode node, NodeConceptHierarchicalViewPanel hierarchyViewPanel) {
        this.node = node;
        this.hierarchyViewPanel = hierarchyViewPanel;
    }

    public void run() {
        Hierarchy<Concept> hierarchy = node.getHierarchy();
        
        ArrayList<ArrayList<Concept>> levels = new ArrayList<>();

        Concept root = hierarchy.getRoot();
        
        HashMap<Concept, Integer> parentCount = new HashMap<>();
        
        Set<Concept> concepts = hierarchy.getNodes();
        
        for(Concept concept : concepts) {
            parentCount.put(concept, hierarchy.getParents(concept).size());
        }
        
        Queue<Concept> queue = new LinkedList<>();
        queue.add(root);
        
        HashMap<Concept, Integer> conceptDepth = new HashMap<>();

        while(!queue.isEmpty()) {
            Concept concept = queue.remove();
            
            Set<Concept> parents = hierarchy.getParents(concept);
            
            int maxParentDepth = -1;
            
            for(Concept parent : parents) {
                int parentDepth = conceptDepth.get(parent);
                
                if(parentDepth > maxParentDepth) {
                    maxParentDepth = parentDepth;
                }
            }
            
            int depth = maxParentDepth + 1;
            
            conceptDepth.put(concept, depth);
            
            if(levels.size() < depth + 1) {
                levels.add(new ArrayList<>());
            }
            
            levels.get(depth).add(concept);

            Set<Concept> children = hierarchy.getChildren(concept);
            
            for(Concept child : children) {
                int childParentCount = parentCount.get(child) - 1;
                
                if(childParentCount == 0) {
                    queue.add(child);
                } else {
                    parentCount.put(child, childParentCount);
                }
            }
        }
        
        ArrayList<ArrayList<ConceptEntry>> levelEntries = new ArrayList<>();

        for (ArrayList<Concept> level : levels) {
            Collections.sort(level, (a,b) -> a.getName().compareTo(b.getName()));

            ArrayList<ConceptEntry> conceptEntries = new ArrayList<>();

            for (Concept c : level) {
                ConceptEntry entry = new ConceptEntry(c);

                conceptEntries.add(entry);
                conceptEntryMap.put(c, entry);
            }
            
            levelEntries.add(conceptEntries);
        }
        
        SwingUtilities.invokeLater( () -> {
            hierarchyViewPanel.initialize(hierarchy, levelEntries, conceptEntryMap);
        });
    }

}
