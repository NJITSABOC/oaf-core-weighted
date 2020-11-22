package edu.njit.cs.saboc.blu.core.graph.nodes;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Chris
 */
public class PartitionedNodeEntry extends AbNNodeEntry {

    private JButton collapseExpand;

    private AbstractionNetworkGraph graph;
    
    /**
     * Index of this area in the ArrayList <i>areas</i> in the GraphLevel class.
     */
    private int nodeX;

    /**
     * The level object this is part of.
     */
    private GraphLevel parentLevel;
    
    /**
     * The GraphRegion objects that represent the regions within this Area.
     */
    private ArrayList<GenericPartitionEntry> partitions = new ArrayList<>();

    /**
     * Initial bounds of the container entry (e.g. not collapsed)
     */
    private Rectangle preferredBounds;

    /**
     * Keeps track of the rows (horizontal lanes) running between pAreas.
     */
    private ArrayList<GraphLane>[] rows = new ArrayList[50];
    
    private boolean isCollapsed = false;

    public PartitionedNodeEntry(
            PartitionedNode node,
            AbstractionNetworkGraph g,
            int aX, 
            GraphLevel parent, 
            Rectangle preferredBounds) {
        
        super(node);

        this.nodeX = aX;
        this.parentLevel = parent;
        this.preferredBounds = preferredBounds;
        this.graph = g;

        //Setup the panel's dimensions, etc.
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black));

        collapseExpand = new JButton();
        collapseExpand.setBounds(2, 2, 12, 12);
        collapseExpand.setBackground(Color.WHITE);
        collapseExpand.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        collapseExpand.setFont(new Font("Tahoma", Font.BOLD, 12));
        collapseExpand.setText("-");
        
        collapseExpand.addActionListener((ActionEvent ae) -> {
            if (isCollapsed) {

                this.expandContainer();
                
                isCollapsed = false;

            } else {
                this.collapseContainer();
                
                isCollapsed = true;

                Collection<? extends PartitionedNodeEntry> areas = graph.getContainerEntries().values();

                for (PartitionedNodeEntry containerEntry : areas) {
                    if (!containerEntry.isCollapsed()) {
                        return;
                    }
                }
            }
        });

        add(collapseExpand);
    }
    
    public PartitionedNode getNode() {
        return (PartitionedNode)super.getNode();
    }

    public void expandContainer() {

        if (graph.getSelectedEdge() != null) {
            graph.deactivateSelectedEdge();
        }

        GraphLevel currentLevel = this.getLevelParent();

        int shift = 0;

        requestFocusInWindow();

        collapseExpand.setText("-");

        for (Component y : getComponents()) {
            if (y instanceof JPanel || y instanceof JButton) {
                y.setVisible(true);
            } else if (y instanceof JLabel) {
                remove(y);
            }
        }

        shift = (int) preferredBounds.getWidth() - getWidth();
        setBounds(getX() - (shift / 2), getY(), (int) preferredBounds.getWidth(), (int) preferredBounds.getHeight());
        rippleExpand(shift / 2);

        currentLevel.cascadeHeightChange(currentLevel.getTallestContainerEntry().getHeight());

        for (GenericPartitionEntry partition : partitions) {
           
            for (GraphGroupLevel groupLevel : partition.getGroupLevels()) {
                if (groupLevel.getIsVisible()) {
                    for (SinglyRootedNodeEntry group : groupLevel.getGroupEntries()) {
                        if (group.isVisible()) {
                            group.showIncidentEdges();
                        }
                    }
                }
            }
        }

        //Inflate the lane between this area and the area directly to the right
        graph.redrawEdges();
    }

    public void collapseContainer() {
        if (graph.getSelectedEdge() != null) {
            graph.deactivateSelectedEdge();
        }

        GraphLevel currentLevel = this.getLevelParent();

        ArrayList<JLabel> collapsedContainerLabels = new ArrayList<JLabel>();
        
        final int MIN_COLLAPSED_WIDTH = 200;
        
        int collapsedContainerHeight = 8;
        
        int shift = 0;
        
        int maxLabelWidth = MIN_COLLAPSED_WIDTH;

        requestFocusInWindow();

        preferredBounds = this.getBounds();
        collapseExpand.setText("+");

        for (Component containerComponent : this.getComponents()) {
            if (containerComponent instanceof GenericPartitionEntry) {
                GenericPartitionEntry partitionEntry = (GenericPartitionEntry)containerComponent;

                JLabel regionLabel = partitionEntry.getLabel();
                
                graph.getLabelManager().removeLabel(regionLabel);
                
                JLabel partitionLabel = graph.getGraphLayout().createPartitionLabel(partitionEntry.getNode(), maxLabelWidth);
                maxLabelWidth = Math.max(maxLabelWidth, partitionLabel.getWidth() + 8);
                
                partitionLabel.setBounds(0, collapsedContainerHeight, partitionLabel.getWidth(), partitionLabel.getHeight());
                
                collapsedContainerLabels.add(partitionLabel);
                collapsedContainerHeight += (partitionLabel.getHeight() + 8);
                
                containerComponent.setVisible(false);
            }
        }
        
        collapsedContainerHeight -= 8;
        
        for(JLabel label : collapsedContainerLabels) {
            label.setBounds((maxLabelWidth - label.getWidth()) / 2, label.getY(), label.getWidth(), label.getHeight());
            
            this.add(label);
        }
        
        int preferredWidth = maxLabelWidth;

        //Collapse the area and pull areas to the right and left towards it
        shift = (int) preferredBounds.getWidth() - preferredWidth;
        this.setBounds(getX() + (shift / 2), getY(), preferredWidth, collapsedContainerHeight + 20);
        
        rippleCollapse(shift / 2);

        currentLevel.cascadeHeightChange(currentLevel.getTallestContainerEntry().getHeight());

        for (GenericPartitionEntry partition : partitions) {
             
            for (GraphGroupLevel groupLevel : partition.getGroupLevels()) {
                if (groupLevel.getIsVisible()) {
                    for (SinglyRootedNodeEntry group : groupLevel.getGroupEntries()) {
                        if (group.isVisible()) {
                            group.hideIncidentEdges();
                        }
                    }
                }
            }
        }

        graph.redrawEdges();
    }

    public void rippleExpand(double dx) {
        ArrayList<PartitionedNodeEntry> containerEntries = parentLevel.getContainerEntries();
        int areaIndex = containerEntries.indexOf(this);

        //Shift areas to the right
        for (int i = areaIndex; i < containerEntries.size(); ++i) {
            containerEntries.get(i).setBounds((int) (containerEntries.get(i).getX() + dx), containerEntries.get(i).getY(),
                    containerEntries.get(i).getWidth(), containerEntries.get(i).getHeight());
        }

        //Shift areas to the left
        for (int j = areaIndex; j >= 0; --j) {
            containerEntries.get(j).setBounds((int) (containerEntries.get(j).getX() - dx), containerEntries.get(j).getY(),
                    containerEntries.get(j).getWidth(), containerEntries.get(j).getHeight());
        }
    }

    public void rippleCollapse(double dx) {
        ArrayList<PartitionedNodeEntry> containerEntries = parentLevel.getContainerEntries();
        int areaIndex = containerEntries.indexOf(this);

        //Shift areas to the right
        for (int i = areaIndex; i < containerEntries.size(); ++i) {
            containerEntries.get(i).setBounds((int) (containerEntries.get(i).getX() - dx), containerEntries.get(i).getY(),
                    containerEntries.get(i).getWidth(), containerEntries.get(i).getHeight());
        }

        //Shift areas to the left
        for (int j = areaIndex; j >= 0; --j) {
            containerEntries.get(j).setBounds((int) (containerEntries.get(j).getX() + dx), containerEntries.get(j).getY(),
                    containerEntries.get(j).getWidth(), containerEntries.get(j).getHeight());
        }
    }

    public void cascadeExpandToRight(int dx) {
        PartitionedNodeEntry containerEntryToRight = this.getContainerEntryRightOf();

        while (containerEntryToRight != null) {
            containerEntryToRight.setBounds(containerEntryToRight.getX() + dx, containerEntryToRight.getY(), containerEntryToRight.getWidth(), containerEntryToRight.getHeight());
            containerEntryToRight = containerEntryToRight.getContainerEntryRightOf();
        }
    }

    public void cascadeCollapseToRight(int dx) {
        PartitionedNodeEntry containerEntryToRight = this.getContainerEntryRightOf();

        while (containerEntryToRight != null) {
            containerEntryToRight.setBounds(containerEntryToRight.getX() - dx, containerEntryToRight.getY(), containerEntryToRight.getWidth(), containerEntryToRight.getHeight());
            containerEntryToRight = containerEntryToRight.getContainerEntryRightOf();
        }
    }
    
    public ArrayList<GenericPartitionEntry> getContainerPartitions() {
        return partitions;
    }

    public PartitionedNode getPartitionedNode() {
        return (PartitionedNode)super.getNode();
    }

    public int getNodeX() {
        return nodeX;
    }

    public int getAbsoluteX() {
        return this.getX();
    }

    public int getAbsoluteY() {
        return this.getY();
    }
    
    public GraphLevel getLevelParent() {
        return parentLevel;
    }

    public GenericPartitionEntry addPartitionEntry(GenericPartitionEntry region) {
        partitions.add(region);
        
        return region;
    }

    public ArrayList<GraphLane> getColumnLeft() {
        return parentLevel.getParentGraph().getGraphLayout().getColumn(nodeX, parentLevel.getLevelY());
    }

    public GraphLevel getParentLevel() {
        return parentLevel;
    }

    public ArrayList<GraphLane> getRow(int y) {
        if (rows.length <= y) {
            return null;
        }

        return rows[y];
    }

    public void setRow(int y, ArrayList<GraphLane> r) {
        if (rows.length <= y) {
            return;
        }

        rows[y] = r;
    }

    public void clearRows() {
        rows = new ArrayList[50]; // Although this is a hard-coded array, there are safety features built in to expand it, if necessary.
    }

    public void addRow(int y, ArrayList<GraphLane> r) {

        if (rows.length <= y) {
            ArrayList<GraphLane>[] rows2 = new ArrayList[y + 50];


            for (int i = 0; i < rows.length; i++) {
                rows2[i] = rows[i];
            }

            rows = rows2;
        }

        rows[y] = r;
    }

    @Override
    public String toString() {
        return "Container: ContainerX = " + nodeX + ", ContainerY = " + getLevelParent().getLevelY() + ", x = " + getAbsoluteX() + ", y = " + getAbsoluteY() + ", width = " + getWidth() + ", height = " + getHeight() + "\n"
                + getPartitionedNode().toString();

    }

    public PartitionedNodeEntry getContainerEntryRightOf() {
        if (nodeX + 1 < parentLevel.getContainerEntries().size()) {
            return parentLevel.getContainerEntries().get(nodeX + 1);
        }

        return null;
    }

    public boolean isLargestPartition(GenericPartitionEntry testPartitionEntry) {
        for (GenericPartitionEntry region : partitions) {
            if (testPartitionEntry.getVisibleGroupLevelCount() <= region.getVisibleGroupLevelCount()
                    && testPartitionEntry != region) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<GenericPartitionEntry> getPartitionEntries() {
        return partitions;
    }

    public int getPartitionCount() {
        return partitions.size();
    }

    public boolean isCollapsed() {
        return (collapseExpand.getText()).equals("+");
    }
}
