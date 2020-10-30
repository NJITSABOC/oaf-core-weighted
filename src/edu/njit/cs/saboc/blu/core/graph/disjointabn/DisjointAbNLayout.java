package edu.njit.cs.saboc.blu.core.graph.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.utils.comparators.SinglyRootedNodeComparator;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.swing.JLabel;

/**
 * A layout for disjoint abstraction networks. Disjoint nodes are organized into
 * groups based on their set of overlaps.
 * 
 * @author Chris O
 * @param <T>
 */
public class DisjointAbNLayout<T extends DisjointAbstractionNetwork> extends AbstractionNetworkGraphLayout<T> {

    // Dummy node for partitioning into common sets
    private class OrganizingPartitionNode extends PartitionedNode {

        public OrganizingPartitionNode() {
            super(new HashSet<>());
        }

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
    }
    
    private class DisjointAbNOverlapPartition {
        
        private final Set<Node> overlaps;
        private final Set<DisjointNode> nodes;
        
        public DisjointAbNOverlapPartition(Set<DisjointNode> nodes) {
            this.overlaps = nodes.iterator().next().getOverlaps();
            this.nodes = nodes;
        }
        
        public Set<Node> getOverlaps() {
            return overlaps;
        }
        
        public Set<DisjointNode> getDisjointNodes() {
            return nodes;
        }
        
        public ArrayList<DisjointNode> getSortedDisjointNodes() {
            ArrayList<DisjointNode> sortedNodes = new ArrayList<>(nodes);
            sortedNodes.sort(new SinglyRootedNodeComparator<>());
            
            return sortedNodes;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            
            hash = 79 * hash + Objects.hashCode(this.overlaps);
            
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            
            if (getClass() != obj.getClass()) {
                return false;
            }
            
            final DisjointAbNOverlapPartition other = (DisjointAbNOverlapPartition) obj;
            
            if (!Objects.equals(this.overlaps, other.overlaps)) {
                return false;
            }
            
            return true;
        }
    }
    
    private class DisjointAbNLevelPartition {
        
        private final int overlapDegree;
        private final Set<DisjointAbNOverlapPartition> overlapPartitions;
        
        public DisjointAbNLevelPartition(Set<DisjointAbNOverlapPartition> overlapPartitions) {
            this.overlapDegree = overlapPartitions.iterator().next().getOverlaps().size();
            this.overlapPartitions = overlapPartitions;
        }
        
        public int getOverlapDegree() {
            return overlapDegree;
        }
        
        public Set<DisjointAbNOverlapPartition> getOverlapPartitions() {
            return overlapPartitions;
        }
        
        public ArrayList<DisjointAbNOverlapPartition> getSortedOverlapPartitions() {
            
            ArrayList<DisjointAbNOverlapPartition> sortedPartitions = new ArrayList<>(this.getOverlapPartitions());
            sortedPartitions.sort((a,b) -> {
                return b.getDisjointNodes().size() - a.getDisjointNodes().size();
            });
            
            return sortedPartitions;
        }
        
    }

    private final T disjointAbN;
    
    private boolean recoloredOverlaps;


    public DisjointAbNLayout(AbstractionNetworkGraph<T> graph, T disjointAbN) {
        super(graph);

        this.disjointAbN = disjointAbN;
        
        this.recoloredOverlaps = false;
    }

    public DisjointAbstractionNetwork getDisjointAbN() {
        return disjointAbN;
    }
    
    public boolean recoloredOverlaps() {
        return this.recoloredOverlaps;
    }
    
    @Override
    public void doLayout() {

        // All of the overlapping nodes, even external to a given subset
        Set<SinglyRootedNode> allOverlappingNodes = disjointAbN.getOverlappingNodes();
        
        Set<DisjointNode> disjointNodes = disjointAbN.getAllDisjointNodes();
        
        Set<SinglyRootedNode> overlappingNodesInAbN = new HashSet<>();
        
        disjointNodes.forEach( (node) -> {
            overlappingNodesInAbN.addAll(node.getOverlaps());
        });
        
        Color[] colors = this.createOverlapColors();
        
        Set<SinglyRootedNode> coloredNodes;
        
        if(allOverlappingNodes.size() > colors.length) {
            if(overlappingNodesInAbN.size() < allOverlappingNodes.size()) {
                
                coloredNodes = overlappingNodesInAbN;
                
                this.recoloredOverlaps = true;
                
            } else {
                coloredNodes = allOverlappingNodes;
            }
        } else {
            coloredNodes = allOverlappingNodes;
        }

        Map<SinglyRootedNode, Color> colorMap = new HashMap<>();

        int colorId = 0;

        for (SinglyRootedNode node : coloredNodes) {
            if (colorId >= colors.length) {
                colorMap.put(node, Color.GRAY);
            } else {
                colorMap.put(node, colors[colorId]);
                colorId++;
            }
        }
        
        Map<Integer, DisjointAbNLevelPartition> levelPartitions = createOverlapLevelPartitions(disjointNodes);
        ArrayList<Integer> overlapLevels = new ArrayList<>(levelPartitions.keySet());
        
        Collections.sort(overlapLevels);

        int containerX = 0;
        int containerY = 0;

        int x = 0;
        int y = 20;

        int rowMaxHeight = 0;
        
        addGraphLevel(new GraphLevel(0, getGraph(), new ArrayList<>()));
        
        for(int level : overlapLevels) {
            addGraphLevel(
                    new GraphLevel(
                            containerY,
                            getGraph(),
                            generateUpperRowLanes(
                                    -5, 
                                    GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 
                                    3, null)));
            
            x = 0;
            y += rowMaxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT;

            containerY++;    
            containerX = 0;  

            rowMaxHeight = 0; 

            DisjointAbNLevelPartition levelPartition = levelPartitions.get(level);
            ArrayList<DisjointAbNOverlapPartition> overlapPartitions;
            
            if(levelPartition.getOverlapDegree() == 1) {
                overlapPartitions = new ArrayList<>(levelPartition.getOverlapPartitions());
                
                overlapPartitions.sort((a,b) -> {
                    int aCount = a.getDisjointNodes().iterator().next().getConceptCount();
                    int bCount = b.getDisjointNodes().iterator().next().getConceptCount();
                    
                    return bCount - aCount;
                });
            } else {
                overlapPartitions = levelPartition.getSortedOverlapPartitions();
            }
            
            for (DisjointAbNOverlapPartition overlapPartition : overlapPartitions) {
                                
                int nodeCount = overlapPartition.getDisjointNodes().size();
                int disjointNodeEntriesWide = (int)Math.ceil(Math.sqrt(nodeCount));
                
                int partitionWidth = disjointNodeEntriesWide * (DisjointNodeEntry.DISJOINT_NODE_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                
                int partitionHeight = (int) (Math.ceil((double) nodeCount / disjointNodeEntriesWide))
                    * (DisjointNodeEntry.DISJOINT_NODE_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);

                int width = partitionWidth + GraphLayoutConstants.GROUP_CHANNEL_WIDTH;
                int height = partitionHeight + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                
                GraphLevel currentLevel = getLevels().get(containerY);
                
                if (height > rowMaxHeight) {
                    rowMaxHeight = height;
                }
                
                EmptyContainerEntry containerEntry = createContainerPanel(x, y, width, height, containerX, currentLevel);
                OrganizingPartitionNode dummyNode = new OrganizingPartitionNode();
                
                getContainerEntries().put(dummyNode, containerEntry);
                
                currentLevel.addContainerEntry(containerEntry);
                
                addColumn(containerX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); 
                
                EmptyContainerPartitionEntry currentPartition = createPartitionPanel(containerEntry, 0, 0, width, height);

                containerEntry.addPartitionEntry(currentPartition);

                currentPartition.addGroupLevel(new GraphGroupLevel(0, currentPartition));

                
                containerEntry.addRow(0, generateUpperRowLanes(-4,
                        GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));

                containerEntry.addRow(0, 
                        generateUpperRowLanes(-4,
                        GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));
                
                ArrayList<DisjointNode> sortedPartitionNodes = overlapPartition.getSortedDisjointNodes();
                
                int nodeIndex = 0;

                int [] groupX = new int[(int)Math.ceil((double) nodeCount / (double) disjointNodeEntriesWide)];

                int x2 = GraphLayoutConstants.GROUP_CHANNEL_WIDTH;
                int y2 = GraphLayoutConstants.GROUP_ROW_HEIGHT;

                int disjointGroupX = 0;
                int disjointGroupY = 0;

                for (DisjointNode group : sortedPartitionNodes) {

                    GraphGroupLevel currentClusterLevel = currentPartition.getGroupLevels().get(disjointGroupY);

                    DisjointNodeEntry targetGroupEntry = createGroupPanel(
                            group, 
                            currentPartition, 
                            x2,
                            y2, 
                            disjointGroupX, 
                            currentClusterLevel, 
                            colorMap);

                    currentPartition.getVisibleGroups().add(targetGroupEntry);

                    currentPartition.addColumn(groupX[disjointGroupY], generateColumnLanes(-3,
                            GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, containerEntry));

                    getGroupEntries().put(group, targetGroupEntry);    // Store it in a map keyed by its ID...

                    currentClusterLevel.addGroupEntry(targetGroupEntry);

                    if ((nodeIndex + 1) % disjointNodeEntriesWide == 0 && nodeIndex < nodeCount - 1) {
                        y2 += DisjointNodeEntry.DISJOINT_NODE_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                        x2 = GraphLayoutConstants.GROUP_CHANNEL_WIDTH;
                        
                        disjointGroupX = 0;

                        groupX[disjointGroupY]++;

                        disjointGroupY++;

                        if (currentPartition.getGroupLevels().size() <= disjointGroupY) {
                            currentPartition.addGroupLevel(new GraphGroupLevel(disjointGroupY, currentPartition));

                            containerEntry.addRow(disjointGroupY, generateUpperRowLanes(-4,
                                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, containerEntry));
                        }
                    } else {
                        x2 += (DisjointNodeEntry.DISJOINT_NODE_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                        disjointGroupX++;
                        groupX[disjointGroupY]++;
                    }

                    nodeIndex++;
                }

                x += width + 10;  // Set x to a position after the newly created area and the appropriate space after that given the set channel width.
                containerX++;
            }
        }

        this.centerGraphLevels(this.getGraphLevels());
        
        super.getGraph().setBounds(0, 0, super.getGraph().getAbNWidth(), super.getGraph().getAbNHeight());
    }

    private Color[] createOverlapColors() {

        int iterations = 4;

        Color[] seedColors = new Color[]{
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW,
            Color.PINK,
            Color.ORANGE,
            Color.CYAN,
            Color.LIGHT_GRAY,
            Color.MAGENTA};

        Color[] colors = new Color[seedColors.length * iterations];

        int index = 0;

        for (index = 0; index < seedColors.length; index++) {
            colors[index] = seedColors[index];
        }

        double scale = 0.8;

        for (int c = 0; c < iterations - 1; c++) {
            for (Color color : seedColors) {
                int r = (int) (color.getRed() * scale);
                int g = (int) (color.getGreen() * scale);
                int b = (int) (color.getBlue() * scale);

                colors[index++] = new Color(r, g, b);
            }

            scale -= 0.2;
        }

        return colors;
    }

    private Map<Integer, DisjointAbNLevelPartition> createOverlapLevelPartitions(Set<DisjointNode> nodes) {
        Map<Set<Node>, Set<DisjointNode>> nodeOverlaps = new HashMap<>();
        
        nodes.forEach( (node) -> {
            if(!nodeOverlaps.containsKey(node.getOverlaps())) {
                nodeOverlaps.put(node.getOverlaps(), new HashSet<>());
            }
            
            nodeOverlaps.get(node.getOverlaps()).add(node);
        });
        
        Set<DisjointAbNOverlapPartition> overlapPartitions = new HashSet<>();
        
        nodeOverlaps.forEach( (overlap, disjointNodes) -> {
            overlapPartitions.add(new DisjointAbNOverlapPartition(disjointNodes));
        });
        
        Map<Integer, Set<DisjointAbNOverlapPartition>> partitionsByLevel = new HashMap<>();
        
        overlapPartitions.forEach( (partition) -> {
            int overlapDegree = partition.getOverlaps().size();
            
            if(!partitionsByLevel.containsKey(overlapDegree)) {
                partitionsByLevel.put(overlapDegree, new HashSet<>());
            }
            
            partitionsByLevel.get(overlapDegree).add(partition);
        });
        
        Map<Integer, DisjointAbNLevelPartition> levels = new HashMap<>();
        
        partitionsByLevel.forEach((level, partitions) -> {
            levels.put(level, new DisjointAbNLevelPartition(partitions));
        });
        
        return levels;
    }

    private DisjointNodeEntry createGroupPanel(DisjointNode node,
            EmptyContainerPartitionEntry parent,
            int x,
            int y,
            int groupX,
            GraphGroupLevel groupLevel,
            Map<SinglyRootedNode, Color> colorMap) {

        ArrayList<SinglyRootedNode> groups = new ArrayList<>(node.getOverlaps());

        Collections.sort(groups, (a, b) -> b.getConceptCount() - a.getConceptCount());

        Color[] disjointColors = new Color[groups.size()];

        for (int c = 0; c < disjointColors.length; c++) {
            disjointColors[c] = colorMap.get(groups.get(c));
        }

        DisjointNodeEntry targetGroupEntry = new DisjointNodeEntry(
                node, 
                getGraph(), 
                parent, 
                groupX, 
                groupLevel, 
                new ArrayList<>(), 
                disjointColors);

        targetGroupEntry = (DisjointNodeEntry) targetGroupEntry.labelOffset(
                new Point(DisjointNodeEntry.DISJOINT_LABEL_OFFSET, 
                        DisjointNodeEntry.DISJOINT_LABEL_OFFSET));

        // Make sure this panel dimensions will fit on the graph, 
        // stretch the graph if necessary
        getGraph().stretchGraphToFitPanel(
                x, 
                y, 
                DisjointNodeEntry.DISJOINT_NODE_WIDTH, 
                DisjointNodeEntry.DISJOINT_NODE_HEIGHT);

        // Setup the panel's dimensions, etc.
        targetGroupEntry.setBounds(
                x, 
                y, 
                SinglyRootedNodeEntry.ENTRY_WIDTH, 
                SinglyRootedNodeEntry.ENTRY_HEIGHT);

        parent.add(targetGroupEntry, 0);

        return targetGroupEntry;
    }

    protected EmptyContainerEntry createContainerPanel(int x, int y, int width, int height, int areaX, GraphLevel parentLevel) {
        EmptyContainerEntry targetPanel = new EmptyContainerEntry(getGraph(), areaX, parentLevel, new Rectangle(x, y, width, height));

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        targetPanel.setBounds(x, y, width, height);

        getGraph().add(targetPanel, 0);

        return targetPanel;
    }

    protected EmptyContainerPartitionEntry createPartitionPanel(EmptyContainerEntry container, int x, int y, int width, int height) {
        EmptyContainerPartitionEntry partitionPanel = new EmptyContainerPartitionEntry(width, height, container, getGraph());

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        partitionPanel.setBounds(x, y, width, height);

        container.add(partitionPanel, 0);

        return partitionPanel;
    }

    @Override
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
        return new JLabel();
    }
}
