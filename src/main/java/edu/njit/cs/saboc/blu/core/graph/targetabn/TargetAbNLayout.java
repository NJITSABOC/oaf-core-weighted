package edu.njit.cs.saboc.blu.core.graph.targetabn;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.targetbased.TargetGroup;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class TargetAbNLayout<T extends TargetAbstractionNetwork<TargetGroup>>  extends AbstractionNetworkGraphLayout<T> {

    private final T targetAbN;
    
    public TargetAbNLayout(AbstractionNetworkGraph<T> graph, T targetAbN) {
        super(graph);
        
        this.targetAbN = targetAbN;
    }

    public void doLayout() {
        ArrayList<ArrayList<TargetGroup>> groupLevels = new ArrayList<>();

        TargetGroup root = targetAbN.getTargetGroupHierarchy().getRoot();
        
        HashMap<TargetGroup, Integer> parentGroupCount = new HashMap<>(); 
        
        parentGroupCount.put(root, 0);
        
        for(TargetGroup group : targetAbN.getTargetGroups()) {
            parentGroupCount.put(group, targetAbN.getTargetGroupHierarchy().getParents(group).size());
        }
        
        Queue<TargetGroup> queue = new LinkedList<>();
        queue.add(root);
        
        HashMap<TargetGroup, Integer> groupDepths = new HashMap<>();

        while(!queue.isEmpty()) {
            TargetGroup group = queue.remove();
            
            Set<TargetGroup> parents = targetAbN.getTargetGroupHierarchy().getParents(group);
            
            int maxParentDepth = -1;
            
            for (TargetGroup parent : parents) {
                int parentDepth = groupDepths.get(parent);

                if (parentDepth > maxParentDepth) {
                    maxParentDepth = parentDepth;
                }
            }

            int depth = maxParentDepth + 1;
            
            groupDepths.put(group, depth);
            
            if(groupLevels.size() < depth + 1) {
                groupLevels.add(new ArrayList<>());
            }
            
            groupLevels.get(depth).add(group);

            Set<TargetGroup> children = targetAbN.getTargetGroupHierarchy().getChildren(group);
            
            for(TargetGroup child : children) {

                int parentCount = parentGroupCount.get(child) - 1;
                
                if(parentCount == 0) {
                    queue.add(child);
                } else {
                    parentGroupCount.put(child, parentCount);
                }
            }
        }
        
        int level = 0;
        
        for(ArrayList<TargetGroup> levelGroups : groupLevels) {
            
            
            Collections.sort(levelGroups, (a, b) -> {
                if(a.getConceptCount() != b.getConceptCount()) {
                    return b.getConceptCount() - a.getConceptCount();
                } else {
                    return a.getRoot().getName().compareToIgnoreCase(b.getRoot().getName());
                }
            });
            
        }
        
        int containerX = 0;  // The first area on each line is given an areaX value of 0.
        int containerY = 0;  // The first row of areas is given an areaY value of 0.
        int x = 0;
        int y = 20;
        
        addGraphLevel(new GraphLevel(0, getGraph(), new ArrayList<>())); // Add the first level of areas (the single pArea 0-relationship level) to the data representation of the graph.

        for (ArrayList<TargetGroup> groupLevel : groupLevels) {  // Loop through the areas and generate the diagram for each of them
            int width = 0;

            int groupCount = groupLevel.size();

            int groupEntriesWide = Math.min(14, groupCount);

            int levelWidth = groupEntriesWide * (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);

            width += levelWidth + 20;

            int height = (int) (Math.ceil((double) groupCount / groupEntriesWide))
                    * (SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);

            width += 20;
            height += 60 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            GraphLevel currentLevel = getLevels().get(containerY);

            TargetContainerEntry containerEntry = createContainerPanel(x, y, width, height, containerX, currentLevel);

            // TODO: Do we still need this?
            
            PartitionedNode dummyNode = new PartitionedNode(new HashSet<>()) {
                @Override
                public String getName(String separator) {
                    return "NULL";
                }

                @Override
                public String getName() {
                    return "NULL";
                }

                @Override
                public boolean equals(Object o) {
                    return this == o;
                }

                @Override
                public int hashCode() {
                    return super.getInternalNodes().hashCode();
                }
                
            };
                       
            super.getContainerEntries().put(dummyNode, containerEntry);

            // Add a data representation for this new area to the current area Level obj.
            currentLevel.addContainerEntry(containerEntry);

            // Generates a column of lanes to the left of this area.
            addColumn(containerX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null));

            int [] groupX = new int[(int)Math.ceil((double)groupLevel.size() / (double)groupEntriesWide)];

            int x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            int y2 = 30;

            int clusterX = 0;
            int clusterY = 0;

            TargetPartitionEntry currentPartition = createPartitionPanel(containerEntry, 0, 0, width, height);
            
            containerEntry.addPartitionEntry(currentPartition);

            currentPartition.addGroupLevel(new GraphGroupLevel(0, currentPartition)); // Add a new pAreaLevel to the data representation of the current Area object.

            containerEntry.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));

            int i = 0;

            for (TargetGroup group : groupLevel) {
                
                Set<Concept> targets = group.getIncomingRelationshipTargets();
                Set<Concept> sources = group.getIncomingRelationshipSources();
                
                GraphGroupLevel currentGroupLevel = currentPartition.getGroupLevels().get(clusterY);
                
                TargetGroupEntry targetGroupEntry = createGroupPanel(group, currentPartition, x2, y2, clusterX, currentGroupLevel);

                currentPartition.getVisibleGroups().add(targetGroupEntry);

                currentPartition.addColumn(groupX[clusterY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, containerEntry));

                getGroupEntries().put(group, targetGroupEntry);    // Store it in a map keyed by its ID...

                currentGroupLevel.addGroupEntry(targetGroupEntry);

                if ((i + 1) % groupEntriesWide == 0 && i < groupLevel.size() - 1) {
                    y2 += SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                    x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    clusterX = 0;
                    
                    groupX[clusterY]++;
                    
                    clusterY++;

                    if (currentPartition.getGroupLevels().size() <= clusterY) {
                        currentPartition.addGroupLevel(new GraphGroupLevel(clusterY, currentPartition)); // Add a new pAreaLevel to the data representation of the current Area object.
                        
                        containerEntry.addRow(clusterY, generateUpperRowLanes(-4,
                                GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));
                    }
                } else {
                    x2 += (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    clusterX++;
                    groupX[clusterY]++;
                }

                i++;
            }

            x = 0;  // Reset the x coordinate to the left
            y += height + GraphLayoutConstants.CONTAINER_ROW_HEIGHT;

            containerY++;    // Update the areaY variable to reflect the new row.
            containerX = 0;  // Reset the areaX variable.

            addGraphLevel(new GraphLevel(containerY, getGraph(),
                    generateUpperRowLanes(-5, GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); // Add a level object to 
        }
        
        this.centerGraphLevels(this.getGraphLevels());
        
        super.getGraph().setBounds(0, 0, this.getAbNWidth(), this.getAbNHeight());
    }
    
     private TargetGroupEntry createGroupPanel(TargetGroup p, TargetPartitionEntry parent, int x, int y, int groupX, GraphGroupLevel clusterLevel) {
         
        TargetGroupEntry targetGroupEntry = new TargetGroupEntry(p, getGraph(), parent, groupX, clusterLevel, new ArrayList<>());

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        getGraph().stretchGraphToFitPanel(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        //Setup the panel's dimensions, etc.
        targetGroupEntry.setBounds(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        parent.add(targetGroupEntry, 0);

        return targetGroupEntry;
    }

    private TargetContainerEntry createContainerPanel(int x, int y, int width, int height, int areaX, GraphLevel parentLevel) {
        TargetContainerEntry targetPanel = new TargetContainerEntry(getGraph(), areaX, parentLevel, new Rectangle(x, y, width, height));

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        targetPanel.setBounds(x, y, width, height);

        getGraph().add(targetPanel, 0);

        return targetPanel;
    }

    private TargetPartitionEntry createPartitionPanel(TargetContainerEntry container, int x, int y, int width, int height) {

        TargetPartitionEntry partitionPanel = new TargetPartitionEntry(width, height, getGraph(), container, Color.WHITE);

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        partitionPanel.setBounds(x, y, width, height);

        container.add(partitionPanel, 0);

        return partitionPanel;
    }
    
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
        return new JLabel();
    }
}