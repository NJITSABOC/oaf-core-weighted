package edu.njit.cs.saboc.blu.core.graph.layout;

import edu.njit.cs.saboc.blu.core.abn.PartitionedAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import javax.swing.JLabel;

/**
 * The base layout for all partitioned abstraction networks. Pre-sorts the 
 * partitioned nodes to display the largest nodes in the center of the graph.
 * 
 * @author Chris O
 * @param <T>
 */
public abstract class PartitionedAbNLayout<T extends PartitionedAbstractionNetwork> extends AbstractionNetworkGraphLayout<T> {
    
    private final Font LABEL_FONT = new Font("Arial", Font.BOLD, 12);
    
    private final int NODE_SPACING = 32;
    
    private final PartitionedAbstractionNetwork abstractionNetwork;
    
    private final PartitionedAbNConfiguration config;

    public PartitionedAbNLayout(AbstractionNetworkGraph<T> graph, 
            PartitionedAbstractionNetwork abstractionNetwork, 
            PartitionedAbNConfiguration config) {
        
        super(graph);

        this.abstractionNetwork = abstractionNetwork;
        
        this.config = config;
    }

    @Override
    public void doLayout() {
        AbstractionNetworkGraph<T> graph = super.getGraph();
        
        ArrayList<PartitionedNode> sortedNodes = new ArrayList<>();
        ArrayList<PartitionedNode> levelNodes = new ArrayList<>();

        ArrayList<PartitionedNode> nodes = new ArrayList<>(abstractionNetwork.getBaseAbstractionNetwork().getNodes());
        
        Comparator<PartitionedNode> levelComparator = (a, b) -> config.getPartitionedNodeLevel(a) - config.getPartitionedNodeLevel(b);

        PartitionedNode lastNode = null;
               
        Collections.sort(nodes, levelComparator);
        
        Comparator<PartitionedNode> nodeSizeComparator = (a, b) -> a.getConcepts().size() - b.getConcepts().size();

        for (PartitionedNode node : nodes) {
            
            if (lastNode != null && config.getPartitionedNodeLevel(lastNode) != config.getPartitionedNodeLevel(node)) {
                
                Collections.sort(levelNodes, nodeSizeComparator);

                for (int c = 0; c < levelNodes.size(); c += 2) {
                    sortedNodes.add(levelNodes.get(c));
                }
                
                int c;

                if (levelNodes.size() % 2 == 0) {
                    c = levelNodes.size() - 1;
                } else {
                    c = levelNodes.size() - 2;
                }

                for (; c >= 1; c -= 2) {
                    sortedNodes.add(levelNodes.get(c));
                }

                levelNodes.clear();
            }

            levelNodes.add(node);

            lastNode = node;
        }

        Collections.sort(levelNodes, nodeSizeComparator);

        int c = 0;

        for (c = 0; c < levelNodes.size(); c += 2) {
            sortedNodes.add(levelNodes.get(c));
        }

        if (levelNodes.size() % 2 == 0) {
            c = levelNodes.size() - 1;
        } else {
            c = levelNodes.size() - 2;
        }

        for (; c >= 1; c -= 2) {
            sortedNodes.add(levelNodes.get(c));
        }

        super.setLayoutGroupContainers(sortedNodes);
        
        int nodeSize = computeNodeEntrySize(abstractionNetwork.getBaseAbstractionNetwork().getNodes());
        
        ArrayList<Color> levelColors = this.getLevelColors();
        
        PartitionedNode lastCreatedNode = null;  
        GraphLevel currentLevel;
        
        int nodeX = 0;
        int nodeY = 0;
        
        int x = 0; 
        int y = 100;
                
        addGraphLevel(new GraphLevel(0, graph, new ArrayList<>()));
        
        for (PartitionedNode node : sortedNodes) {
            if (lastCreatedNode != null && config.getPartitionedNodeLevel(lastCreatedNode) != config.getPartitionedNodeLevel(node)) {
                x = 0;  
                y += nodeSize + NODE_SPACING; 

                nodeY++;
                nodeX = 0;

                addGraphLevel(new GraphLevel(nodeY, graph, 
                        generateUpperRowLanes(-5, GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); 
            }

            JLabel nodeLabel = createEntryLabel(
                    abstractionNetwork, 
                    node,
                    nodeSize);
            
            currentLevel = super.getLevels().get(nodeY);

            Color color = levelColors.get(nodeY % levelColors.size());
            
            PartitionedNodeEntry nodeEntry = createPartitionEntry(node, x, y, nodeSize, color, nodeX, currentLevel); // Create the area

            super.getContainerEntries().put(node, nodeEntry);

            currentLevel.addContainerEntry(nodeEntry);    // Add a data representation for this new area to the current area Level obj.

            addColumn(nodeX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); // Generates a column of lanes to the left of this area.
            
            int labelXPos =  (nodeSize - nodeLabel.getWidth()) / 2;
            int labelYPos = (nodeSize - nodeLabel.getHeight()) / 2;
            
            nodeLabel.setLocation(labelXPos, labelYPos);

            GenericPartitionEntry dummyPartitionEntry = createDummyPartitionEntry(nodeEntry, 0, 0, nodeSize, color, nodeLabel);
            nodeEntry.addPartitionEntry(dummyPartitionEntry);
            
            x += nodeSize + NODE_SPACING;
            
            nodeX++;
            
            lastCreatedNode = node;
        }
        
        this.centerGraphLevels(this.getGraphLevels(), nodeSize);
        
        graph.setBounds(0, 0, graph.getAbNWidth(), graph.getAbNHeight());
    }
    
    private int computeNodeEntrySize(Set<PartitionedNode> nodes) {
        
        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(LABEL_FONT);
        
        int maxWidth = fontMetrics.stringWidth("100000 Concepts");
        int maxHeight = 0;
        
        for(PartitionedNode node : nodes) {
            int level = config.getPartitionedNodeLevel(node);
            int maxNameWidth = 0;
            
            String name = node.getName("\t");
            
            String [] nameParts = name.split("\t");
            
            for (String relName : nameParts) {
                int width = fontMetrics.stringWidth(relName);

                if (width > maxNameWidth) {
                    maxNameWidth = width;
                }
            }

            int height = (level + 3) * fontMetrics.getHeight();

            if (height > maxHeight) {
                maxHeight = height;
            }

            if (maxNameWidth > maxWidth) {
                maxWidth = maxNameWidth;
            }
        }
        
        int result = Math.max(maxWidth, maxHeight);
        
        result = ((result / 100) + 1) * 100;
        
        result += 10;
        
        return result;
    }
    
    private JLabel createEntryLabel(
            PartitionedAbstractionNetwork abstractionNetwork, 
            PartitionedNode node, 
            int boundingWidth) {
        
        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(LABEL_FONT);

        ArrayList<String> entries = new ArrayList<>();
        
        String name = node.getName("\t");
        String [] nameParts = name.split("\t");

        entries.addAll(Arrays.asList(nameParts));
        
        int conceptCount = node.getConceptCount();
        int internalNodeCount = node.getInternalNodes().size();
        
        String internalNodeCountStr;
        
        String conceptTypeName = config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(conceptCount > 1);
        String conceptCountStr = String.format("%d %s", conceptCount, conceptTypeName);
        
        String internalNodeTypeName = config.getTextConfiguration().getNodeTypeName(internalNodeCount > 1);
        internalNodeCountStr = String.format("%d %s", internalNodeCount, internalNodeTypeName);

        entries.add(conceptCountStr);
        entries.add(internalNodeCountStr);
        
        int maxLineWidth = 0;
        
        for(String entry : entries) {
            int width = fontMetrics.stringWidth(entry);
            
            if(width > maxLineWidth) {
                maxLineWidth = width;
            }
        }
        
        entries = new ArrayList<>(entries.subList(0, entries.size() - 2));
        
        int lineCount = entries.size() + 3;
                
        String labelStr = "<html><center>";
                
        for (String entry : entries) {
            labelStr += String.format("%s<br>", entry);
        }
        
        labelStr += String.format("<br>%s<br>", conceptCountStr);
        labelStr += internalNodeCountStr;
        

        JLabel partitionLabel = new JLabel();
        partitionLabel.setFont(LABEL_FONT);

        partitionLabel.setText(labelStr);

        partitionLabel.setBounds(0, 0, maxLineWidth, lineCount * fontMetrics.getHeight());

        return partitionLabel;
    }
    
    private PartitionedNodeEntry createPartitionEntry(
            PartitionedNode node,
            int x,
            int y,
            int nodeSize,
            Color c,
            int nodeX,
            GraphLevel parentLevel) {
        
        PartitionedNodeEntry nodePanel = new PartitionedNodeEntry(node, super.getGraph(), nodeX, parentLevel, new Rectangle(x, y, nodeSize, nodeSize));

        getGraph().stretchGraphToFitPanel(x, y, nodeSize, nodeSize);

        nodePanel.setBounds(x, y, nodeSize, nodeSize);
        nodePanel.setBackground(c);

        getGraph().add(nodePanel, 0);

        return nodePanel;
    }

    protected GenericPartitionEntry createDummyPartitionEntry(
            PartitionedNodeEntry parentEntry, 
            int x, 
            int y, 
            int nodeSize, 
            Color color, 
            JLabel label) {

        GenericPartitionEntry entry = new GenericPartitionEntry(null, "", nodeSize, nodeSize, getGraph(), parentEntry, color);
        entry.setLabel(label);

        getGraph().stretchGraphToFitPanel(x, y, nodeSize, nodeSize);

        entry.setBounds(x, y, nodeSize, nodeSize);

        parentEntry.add(entry, 0);

        return entry;
    }
    
    protected void centerGraphLevels(ArrayList<GraphLevel> graphLevels, int areaSize) {        
        int maxWidth = graphLevels.get(0).getContainerEntries().size();
        
        for(int c = 1; c < graphLevels.size(); c++) {
            int levelWidth = graphLevels.get(c).getContainerEntries().size();
            
            if(levelWidth > maxWidth) {
                maxWidth = levelWidth;
            }
        }
        
        for(GraphLevel level : graphLevels) {
            
            int levelOffset = maxWidth - level.getContainerEntries().size();
            
            levelOffset /= 2;
            
            for(PartitionedNodeEntry entry : level.getContainerEntries()) {
                Point location = entry.getLocation();
                location.x += levelOffset * (areaSize + NODE_SPACING);
                
                entry.setLocation(location);
            }
        }
    }

    protected abstract ArrayList<Color> getLevelColors();
    
    @Override
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
        return new JLabel("[EMPTY LABEL]");
    }
}
