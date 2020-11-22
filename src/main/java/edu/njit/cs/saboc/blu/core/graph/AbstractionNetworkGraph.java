package edu.njit.cs.saboc.blu.core.graph;

import edu.njit.cs.saboc.blu.core.abn.AbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdge;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphEdgeHandle;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLane;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.GenericPartitionEntry;
import edu.njit.cs.saboc.blu.core.gui.dialogs.ContainerResize;
import edu.njit.cs.saboc.blu.core.gui.dialogs.NodeEditMenu;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.AbNLabelManager;
import edu.njit.cs.saboc.blu.core.gui.gep.utils.drawing.SinglyRootedNodeLabelCreator;
import edu.njit.cs.saboc.blu.core.gui.graphframe.GenericInternalGraphFrame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;


/**
 * The view of an abstraction network. 
 * 
 * TODO: Remove the use of Swing objects, move edge functionality to separate class
 *
 * @author David Daudelin 
 * @author Chris O
 * @param <T>
 */
public class AbstractionNetworkGraph<T extends AbstractionNetwork> extends JLayeredPane {
    
    /**
     * Defines the thickness of the edges.
     */
    protected static final int EDGE_WIDTH = 2;
    
    /**
     * Defines a set of colors to be used for the lines.
     */
    protected static final Color[][] lineColors = {
        {Color.black,
            Color.blue,
            new Color(180, 4, 4),
            new Color(138, 8, 134),
            new Color(223, 1, 116)},
        {new Color(180, 95, 4),
            new Color(255, 0, 255),
            new Color(8, 138, 133),
            new Color(8, 75, 138),
            new Color(134, 138, 8)}};
    
    /**
     * This is a list of all edges currently drawn.
     */
    private final ArrayList<GraphEdge> edges = new ArrayList<>();
    
    /**
     * This is a list of all edges which have been manually adjusted.
     */
    private final ArrayList<GraphEdge> manualEdges = new ArrayList<>();
    
    /**
     * This keeps track of which edge "lanes" are currently filled with an edge.
     */
    private final ArrayList<GraphLane> occupiedLanes = new ArrayList<>();
    
    /**
     * This stores the currently selected edge, if any.
     */
    private GraphEdge selectedEdge;
    
    /**
     * This stores the edge currently being hovered over, if any.
     */
    private GraphEdge hoveredEdge;
    
    /**
     * Stores the JPanel for the handle that is currently being dragged. This is
     * used to preserve the grip on the handle even when the mouse moves off it.
     */
    private GraphEdgeHandle lastDragged;
    
    /**
     * Used to store the width of the graph.
     */
    private int panelWidth = 0;
    /**
     * Used to store the height of the graph.
     */
    private int panelHeight = 0;

    /*
     * Used to map each JPanel segment of a line to the data structure for it's entire line.
     */
    private final HashMap<JPanel, GraphEdge> segmentToEdge = new HashMap<>();
    /**
     * Used to keep track of the currently visible handles
     */
    private final ArrayList<GraphEdgeHandle> activeHandles = new ArrayList<>();

    /**
     * Popup menu displayed when a pArea is right-clicked
     */
    private NodeEditMenu groupMenu;
    
    /**
     * Popup menu displayed when an edge is right-clicked
     */
    private final JPopupMenu edgeMenu = new JPopupMenu();
    
    /**
     * Popup menu displayed when a region is right-clicked
     */
    private JPopupMenu partitionMenu;

    /**
     * Stores the data for the hierarchy this graph represents.
     */
    private final T abstractionNetwork;

    /**
     * Keeps track of the currently selected region
     */
    private GenericPartitionEntry currentPartition;
    
    /**
     * Keeps track of the currently selected pArea
     */
    private SinglyRootedNodeEntry currentGroup;

    private AbstractionNetworkGraphLayout<T> layout;
    
    private final AbNLabelManager labelManager;

    /**
     * Sets up the graph based on the hierarchy information passed in.
     * @param abstractionNetwork
     * @param labelCreator
     * 
     */
    public AbstractionNetworkGraph(T abstractionNetwork, SinglyRootedNodeLabelCreator labelCreator) {
        this.abstractionNetwork = abstractionNetwork;
        
        this.groupMenu = new NodeEditMenu(this);
        
        this.labelManager = new AbNLabelManager(abstractionNetwork, labelCreator);
        
        this.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent me) {
                partitionMenu.setVisible(false);
                groupMenu.setVisible(false);
                
                deactivateSelectedEdge();
                
                currentPartition = null;
                currentGroup = null;
            }
        });
        
        partitionMenu = new JPopupMenu();
        partitionMenu.setFocusable(true);

        JMenuItem resizeMenuItem = new JMenuItem("Resize Container");

        resizeMenuItem.addActionListener((ae) -> {
            
            ContainerResize containerResize = new ContainerResize(currentPartition, this);
            
            partitionMenu.setVisible(false);
        });

        partitionMenu.add(resizeMenuItem);
        
        setOpaque(true);
        setBackground(Color.white);
        setLayout(null);
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        setVisible(true);
        setFocusable(true);

        initializeEdgeMenu();
    }
    
    
    public void setAbstractionNetworkLayout(AbstractionNetworkGraphLayout<T> layout) {
        this.removeAll();
        
        this.edges.clear();
        this.manualEdges.clear();
        this.occupiedLanes.clear();
        
        this.labelManager.clearLabelImages();
        
        this.layout = layout;
        
        this.layout.doLayout();
    }
    
    /**
     * This updates the edges which have been manually adjusted in the past. It only updates the first and last points connecting to the pAreas so as to preserve the manual changes which were made.
     * @param e The edge to adjust.
     */
    public void updateManualEdge(GraphEdge e) {
        SinglyRootedNodeEntry source = e.getSource();
        SinglyRootedNodeEntry target = e.getTarget();

        e.setPoint(0, new Point(source.getAbsoluteX() + source.getWidth() / 2, source.getAbsoluteY()));
        e.setPoint(1, new Point(source.getAbsoluteX() + source.getWidth() / 2, (int) e.getPoints().get(1).getY()));

        e.setPoint(e.getPoints().size() - 2, new Point(target.getAbsoluteX() + target.getWidth() / 2, (int) e.getPoints().get(e.getPoints().size() - 2).getY()));
        e.setPoint(e.getPoints().size() - 1, new Point(target.getAbsoluteX() + target.getWidth() / 2, target.getAbsoluteY() + target.getHeight()));

        e.updateEdge();
    }

    /**
     * Draws an orthogonally routed edge between two pAreas - avoiding overlap with other objects.
     * 
     * @param from
     * @param to
     */
    public void drawRoutedEdge(SinglyRootedNodeEntry from, SinglyRootedNodeEntry to) {

        GraphLane l;
        int tempX;
        int tempY;

        int currentLevel = from.getParentLevel().getLevelY();
        int targetLevel = to.getParentLevel().getLevelY();

        ArrayList<PartitionedNodeEntry> tempAreas;
        PartitionedNodeEntry tempArea;

        ArrayList<Point> pList = new ArrayList<>();

        l = nextLane(from.getGroupLevelParent().getRowAbove());     // Get the next available lane above the pArea if there is one

        if (l == null) {    // If there isn't an available lane...
            GraphEdge newEdge = new GraphEdge(from, to, null);    // Create an object for this edge
            
            edges.add(newEdge); // Add it to the list of current edges,
            layout.resizeGroupRow(from.getGroupLevelParent().getRowAbove(), from.getParentLevel().getLevelY(), 5, from); // Add lanes to the row you want to enter
            
            return; // Abort this method because resizePAreaRow takes care of drawing the edge from here.
        }

        l.setEmpty(false);  // Mark the lane we're going to occupy as full.
        
        occupiedLanes.add(l);
        
        tempY = from.getAbsoluteY() + l.getPosY();
        tempX = from.getAbsoluteX() + from.getWidth() / 2;
        pList.add(new Point(tempX, from.getAbsoluteY()));  // This is the point in the middle of the upper edge of the pArea.
        pList.add(new Point(tempX, tempY));    // This is the point directly above the center of the upper edge of the pArea.

        // If the edge is on the right half of an area and is not in the rightmost area of the level, 
        // it should be routed to the right side of this Area.
        if (from.getAbsoluteX() - from.getParentContainer().getAbsoluteX()
                > from.getParentContainer().getWidth() / 2
                && getLevels().get(from.getParentLevel().getLevelY()).getContainerEntries().size()
                > from.getGroupLevelParent().getParentPartition().getParentContainer().getNodeX() + 1) {

            PartitionedNodeEntry nextOver = getLevels().get(from.getParentLevel().getLevelY()).getContainerEntries().get(from.getParentContainer().getNodeX() + 1);

            l = nextLane(nextOver.getColumnLeft());

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(from, to, null);
                edges.add(newEdge);
                layout.resizeColumn(nextOver.getColumnLeft(), 5, nextOver);
                
                return;
            }

            tempX = nextOver.getAbsoluteX() + l.getPosX();

        } //Otherwise, the edge should be routed to the left side of this area.
        else {
            l = nextLane(from.getParentContainer().getColumnLeft()); // Get the next available lane to the left of this pArea's parent Area if there is one.

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(from, to, null);
                edges.add(newEdge);
                layout.resizeColumn(from.getParentContainer().getColumnLeft(), 5, from.getParentContainer());
                return;
            }

            tempX = from.getParentContainer().getAbsoluteX() + l.getPosX();
        }


        l.setEmpty(false);
        occupiedLanes.add(l);
        pList.add(new Point(tempX, tempY));

        currentLevel--; // Now we're moving up one level...

        l = nextLane(from.getParentLevel().getRowAbove());   // Get the next available lane above this pArea's parent Area if there is one.
        if (l == null) {
            GraphEdge newEdge = new GraphEdge(from, to, null);
            edges.add(newEdge);
            layout.resizeRow(from.getParentLevel().getRowAbove(), from.getParentLevel().getLevelY(), 5, from.getParentContainer());
            return;
        }
        
        l.setEmpty(false);
        occupiedLanes.add(l);
        
        tempY = l.getPosY() + from.getParentLevel().getY();
        pList.add(new Point(tempX, tempY));

        while (currentLevel > targetLevel) // While we're still not in the row directly below our target pArea's level...
        {
            tempAreas = getLevels().get(currentLevel).getContainerEntries();

            tempArea = nearestColumnContainerEntry(tempAreas, tempX); // Gets the area with the left column nearest to the current position.
            
            l = nextLane(tempArea.getColumnLeft());

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(from, to, null);
                edges.add(newEdge);
                layout.resizeColumn(tempArea.getColumnLeft(), 5, tempArea);
                return;
            }

            l.setEmpty(false);
            occupiedLanes.add(l);
            tempX = tempArea.getAbsoluteX() + l.getPosX();
            pList.add(new Point(tempX, tempY));

            currentLevel--; // Moving up one level...

            l = nextLane(tempAreas.get(0).getLevelParent().getRowAbove());   // Get the next available lane above this level.

            if (l == null) {
                GraphEdge newEdge = new GraphEdge(from, to, null);
                edges.add(newEdge);
                layout.resizeRow(tempAreas.get(0).getLevelParent().getRowAbove(), tempAreas.get(0).getLevelParent().getLevelY(), 5, tempAreas.get(0));
                return;
            }

            l.setEmpty(false);
            occupiedLanes.add(l);
            tempY = l.getPosY() + tempAreas.get(0).getLevelParent().getY();
            pList.add(new Point(tempX, tempY));
        }

        l = nextLane(to.getColumnLeft());   // Get the next available lane to the left of the target pArea.

        if (l == null) {
            GraphEdge newEdge = new GraphEdge(from, to, null);
            edges.add(newEdge);
            layout.resizeGroupColumn(to.getColumnLeft(), 5, to);
            return;
        }

        l.setEmpty(false);
        
        occupiedLanes.add(l);
        
        tempX = to.getAbsoluteX() + l.getPosX();
        pList.add(new Point(tempX, tempY));

        tempY = to.getAbsoluteY() + to.getEntryHeight() + 3;   // The point to the left and below the target pArea.
        pList.add(new Point(tempX, tempY));

        tempX = to.getAbsoluteX() + to.getEntryWidth() / 2;    // Now directly below the target pArea.
        pList.add(new Point(tempX, tempY));

        tempY = to.getAbsoluteY() + to.getEntryHeight();       // Final destination, touching the center of the lower edge of the target pArea.

        pList.add(new Point(tempX, tempY));

        GraphEdge dataEdge = new GraphEdge(from, to, pList);
        addEdge(pList, lineColors[from.getGroupLevelParent().getGroupLevelY() % 2][from.getGroupX() % 5], dataEdge, from, to);
        
        edges.add(dataEdge);
    }

    /**
     * This passes back the next available GraphLane object in an ArrayList of such objects that is closest to the middle of the "road". 
     * @param road The ArrayList of GraphLane objects that make up a column or row.
     * @return The GraphLane closest to the middle of the given road.
     */
    private GraphLane nextLane(ArrayList<GraphLane> road) {

        int m = road.size() / 2;
        int i = 0;

        while (i <= m) {
            if ((i + m) < road.size() && road.get(i + m).isEmpty()) {
                return road.get(i + m);
            }

            if ((m - i) >= 0 && road.get(m - i).isEmpty()) {
                return road.get(m - i);
            }

            i++;
        }

        return null;
    }


    /**
     * Redraws all automatically routed edges and updates those which have been manually adjusted.
     */
    public void redrawEdges() {
        occupiedLanes.forEach((l2) -> {
            l2.setEmpty(true);
        });

        ArrayList<GraphEdge> edgesCopy = (ArrayList<GraphEdge>) edges.clone();
        removeEdges(edgesCopy);
        edges.clear();
        
        edgesCopy.forEach((e) -> {
            if (manualEdges.contains(e)) {
                updateManualEdge(e);
            } else {
                drawRoutedEdge(e.getSource(), e.getTarget());
            }
        });

        repaint();
    }

    /**
     * Returns the Area which has a left column closest to the given x-coordinate.
     * @param areas The areas to search through.
     * @param x The x-coordinate.
     * @return The Area which has a left column closest to the given x-coordinate.
     */
    private PartitionedNodeEntry nearestColumnContainerEntry(ArrayList<PartitionedNodeEntry> containers, int x) {
        PartitionedNodeEntry result = null;
        int closestDist = Integer.MAX_VALUE;

        for (PartitionedNodeEntry a : containers) {
            if (Math.abs(a.getAbsoluteX() - x) < closestDist) {
                closestDist = Math.abs(a.getAbsoluteX() - x);
                result = a;
            }
        }

        return result;
    }

    /**
     * Adds an edge to the graph
     * @param points A list of the points in the edge.
     * @param c The color of the edge.
     * @param e The GraphEdge object for this edge.
     * @param source The BluPArea object representing the source pArea for this edge.
     * @param destination The BluPArea object representing the target pArea for this edge.
     */
    public void addEdge(ArrayList<Point> points, Color c, GraphEdge e,
            SinglyRootedNodeEntry source, SinglyRootedNodeEntry destination) {

        for (int i = 0; i < points.size() - 1; i++) {
            JPanel s = new JPanel();

            int width = (int) (points.get(i + 1).getX() - points.get(i).getX());
            
            int x;

            if (width == 0) {
                s.setName("Vertical");
                width = EDGE_WIDTH;
                x = (int) points.get(i).getX();
            } else if (width > 0) {
                x = (int) points.get(i).getX();
                width = width + EDGE_WIDTH - 1;
            } else {
                x = (int) points.get(i).getX() + width;
                width = width * -1 + EDGE_WIDTH - 1;
            }

            int height = (int) (points.get(i + 1).getY() - points.get(i).getY());
            int y = 0;

            if (height == 0) {
                s.setName("Horizontal");
                height = EDGE_WIDTH;
                y = (int) points.get(i).getY();
                width++;
            } else if (height > 0) {
                y = (int) points.get(i).getY();
                height = height + EDGE_WIDTH - 1;
            } else {
                y = (int) points.get(i).getY() + height;
                height = height * -1 + EDGE_WIDTH - 1;
            }

            s.setBounds(x, y, width, height);
            s.setBackground(c);

            if (x + width > panelWidth - 10) {
                panelWidth = x + width + 10;
                updateSize();
            }

            if (y + height > panelHeight - 10) {
                panelHeight = y + height + 10;
                updateSize();
            }

            add(s, new Integer(2));

            e.addSegment(s);
            segmentToEdge.put(s, e);
            s.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseEntered(MouseEvent e) {
                    GraphEdge edge = segmentToEdge.get((JPanel) e.getComponent());

                    if (selectedEdge == null && hoveredEdge != edge) {
                        if (hoveredEdge != null) {
                            hoveredEdge.getSegments().forEach((s) -> {
                                
                                if (s.getName().equals("Vertical")) {
                                    s.setBounds(s.getX(), s.getY(), EDGE_WIDTH, s.getHeight());
                                } else if (s.getName().equals("Horizontal")) {
                                    s.setBounds(s.getX(), s.getY(), s.getWidth() - 2, EDGE_WIDTH);
                                } else {
                                    System.out.println("**ERROR - Edge layout not specified**");
                                }
                                
                            });
                        }


                        hoveredEdge = edge;

                        for (int i = 0; i < edge.getSegments().size(); i++) {
                            JPanel s = edge.getSegments().get(i);

                            if (s.getName().equals("Vertical")) {
                                s.setBounds(s.getX(), s.getY(), EDGE_WIDTH + 2, s.getHeight());
                            } else if (s.getName().equals("Horizontal")) {
                                s.setBounds(s.getX(), s.getY(), s.getWidth() + 2, EDGE_WIDTH + 2);
                            } else {
                                System.out.println("**ERROR - Edge layout not specified**");
                            }
                        }
                    }
                }

                public void mouseExited(MouseEvent e) {
                    if (selectedEdge == null && hoveredEdge != null && hoveredEdge == segmentToEdge.get((JPanel) e.getComponent())) {
                        hoveredEdge.getSegments().forEach((s) -> {
                            
                            if (s.getName().equals("Vertical")) {
                                s.setBounds(s.getX(), s.getY(), EDGE_WIDTH, s.getHeight());
                            } else if (s.getName().equals("Horizontal")) {
                                s.setBounds(s.getX(), s.getY(), s.getWidth() - 2, EDGE_WIDTH);
                            } else {
                                System.out.println("**ERROR - Edge layout not specified**");
                            }
                        });

                        hoveredEdge = null;
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    GraphEdge edge = segmentToEdge.get((JPanel) e.getComponent());

                    if (selectedEdge != edge) {
                        if (selectedEdge != null) {
                            deactivateSelectedEdge();

                            activeHandles.forEach((h) -> {
                                remove(h);
                            });

                            activeHandles.clear();
                        }

                        selectedEdge = edge;

                        int widthBump = -1;

                        if (hoveredEdge != null) {
                            hoveredEdge = null;
                        } else {
                            widthBump = 1;
                        }

                        GraphEdgeHandle handle = null;

                        for (int i = 0; i < edge.getSegments().size(); i++) {
                            JPanel s = edge.getSegments().get(i);

                            if (s.getName().equals("Vertical")) {
                                s.setBounds(s.getX(), s.getY(), EDGE_WIDTH + 1, s.getHeight());
                                handle = new GraphEdgeHandle(s.getX() + EDGE_WIDTH / 2 - 5, s.getY() + s.getHeight() / 2 - 6, s, AbstractionNetworkGraph.this);
                            } else if (s.getName().equals("Horizontal")) {
                                s.setBounds(s.getX(), s.getY(), s.getWidth() + widthBump, EDGE_WIDTH + 1);
                                handle = new GraphEdgeHandle(s.getX() + s.getWidth() / 2 - 6, s.getY() + EDGE_WIDTH / 2 - 5, s, AbstractionNetworkGraph.this);
                            } else {
                                System.out.println("**ERROR - Edge layout not specified**");
                            }

                            if (i != 0 && i != edge.getSegments().size() - 1) {
                                AbstractionNetworkGraph.this.add(handle, new Integer(5));
                                activeHandles.add(handle);
                            }
                        }

                        repaint();

                    } else {
                        if (e.getClickCount() == 2) {
                            try {
                                GenericInternalGraphFrame igf = AbstractionNetworkGraph.this.getParentInternalFrame();

                                if (e.isControlDown()) {
                                    igf.focusOnComponent(getNodeEntries().get(edge.getSource()));
                                } else {
                                    igf.focusOnComponent(getNodeEntries().get(edge.getTarget()));
                                }

                            } catch (Exception exception) {
                                System.err.println(exception);
                            }
                        }
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        edgeMenu.show(e.getComponent(), e.getX(), e.getY());
                        edgeMenu.requestFocus();
                    }
                }
            });
        }
        source.addIncidentEdge(e);
        destination.addIncidentEdge(e);
        repaint();
    }

    public void setGraphWidth(int width) {
        this.panelWidth = width;
    }

    public void setGraphHeight(int height) {
        this.panelHeight = height;
    }
    
     /**
     * Checks to see if the graph is large enough to contain a new object with 10 pixels padding and if not, makes it larger.
     * @param x The x coordinate where this object is to be positioned.
     * @param y The y coordinate where this object is to be positioned.
     * @param width The width of this new object.
     * @param height The height of this new object.
     */
    public void stretchGraphToFitPanel(int x, int y, int width, int height) {

        if (x + width > panelWidth - 10) {
            panelWidth = x + width + 10;
            updateSize();
        }

        if (y + height > panelHeight - 10) {
            panelHeight = y + height + 10;
            updateSize();
        }
    }

    /**
     * Removes this edge from the graph.
     * @param e The edge to be removed.
     */
    public void removeEdge(GraphEdge e) {
        if (e != null) {
            edges.remove(e);
            
            e.getSource().removeIncidentEdge(e);
            e.getTarget().removeIncidentEdge(e);
            
            e.getSegments().forEach((s) -> {
                remove(s);
            });

            if (selectedEdge != null && selectedEdge.equals(e)) {
                activeHandles.forEach((h) -> {
                    remove(h);
                });

                activeHandles.clear();
                selectedEdge = null;
            }
        }
        repaint();
    }

    public void clearAllEdges() {

        ArrayList<GraphEdge> removeEdges = new ArrayList<>();

        edges.forEach((temp) -> {
            removeEdges.add(temp);
        });

        removeEdges(removeEdges);
        edges.removeAll(removeEdges);

        redrawEdges();
    }

    /**
     * Deletes the edges specified in the ArrayList "objects".
     * @param objects The edges to be deleted.
     */
    public void removeEdges(ArrayList<GraphEdge> objects) {
        objects.forEach((e) -> {
            removeEdge(e);
        });

        repaint();
    }

    /**
     * Sets the background and foreground colors for the objects in the ArrayList "objects"
     * @param c The new background color.
     * @param t The new foreground color.
     * @param objects The objects to recolor.
     */
    public void setColors(Color c, Color t, ArrayList<JPanel> objects) {
        objects.forEach((o) -> {
            setColor(c, t, o);
        });
    }

    /**
     * Set the background and foreground color for a single object
     * @param c The new background color.
     * @param t The new foreground color.
     * @param o The object to recolor.
     */
    public void setColor(Color c, Color t, JPanel o) {
        JPanel j = o;
        j.setBackground(c);
        
        JLabel l = (JLabel) j.getComponent(0);
        l.setForeground(t);
    }

    /**
     * Updates the size of the graph.
     */
    public void updateSize() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));

        if (getParent() != null) {
            getParent().validate();
        }
    }

    /**
     * Unselects the currently clicked on edge.
     */
    public void deactivateSelectedEdge() {
        if (selectedEdge != null) {
            
            selectedEdge.getSegments().forEach((s) -> {
                
                if (s.getName().equals("Vertical")) {
                    s.setBounds(s.getX(), s.getY(), EDGE_WIDTH, s.getHeight());
                } else if (s.getName().equals("Horizontal")) {
                    s.setBounds(s.getX(), s.getY(), s.getWidth() - 1, EDGE_WIDTH);
                } else {
                    System.err.println("**ERROR - Edge layout not specified**");
                }
                
            });

            activeHandles.forEach((h) -> {
                remove(h);
            });

            activeHandles.clear();
            repaint();

            selectedEdge = null;
        }
    }

    public GenericInternalGraphFrame getParentInternalFrame() throws Exception {
        Component c = getParent();

        while (!(c instanceof GenericInternalGraphFrame)) {
            c = c.getParent();
        }

        if (c == null) {
            throw new Exception("BluGraph not in an internal frame");
        }

        return ((GenericInternalGraphFrame) c);
    }


    /**
     * Returns true if a line has already been drawn between the given source and target.
     * @param source Concept id of the source pArea
     * @param target Concept id of the target pArea
     * @return A boolean variable that is true if a line has already been drawn between the given source and target and false otherwise.
     */
    public boolean edgeAlreadyDrawn(SinglyRootedNodeEntry source, SinglyRootedNodeEntry target) {
        for (GraphEdge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                return true;
            }
        }

        return false;
    }

    public GraphEdge getSelectedEdge() {
        return selectedEdge;
    }

    public ArrayList<GraphEdgeHandle> getActiveHandles() {
        return activeHandles;
    }

    public GraphEdgeHandle getLastDragged() {
        return lastDragged;
    }

    public void setLastDragged(GraphEdgeHandle h) {
        lastDragged = h;
    }

    public HashMap<JPanel, GraphEdge> getSegmentToEdge() {
        return segmentToEdge;
    }

    public JPopupMenu getGroupEntryMenuFor(SinglyRootedNode node) {
        deactivateSelectedEdge();
        partitionMenu.setVisible(false);
        currentPartition = null;
        
        groupMenu.setCurrentGroup(node);
        
        return groupMenu;
    }

    public T getAbstractionNetwork() {
        return abstractionNetwork;
    }

    public ArrayList<GraphEdge> getEdges() {
        return edges;
    }

    public void addToManualEdges(GraphEdge e) {
        manualEdges.add(e);
    }

    public JPopupMenu getPartitionMenu() {
        deactivateSelectedEdge();
        
        groupMenu.setVisible(false);
        groupMenu.setCurrentGroup(null);
        
        currentGroup = null;
        
        return partitionMenu;
    }

    public ArrayList<GraphLevel> getLevels() {
        return layout.getGraphLevels();
    }
    
    public void setCurrentPartitionEntry(GenericPartitionEntry r) {
        currentPartition = r;
    }

    public GenericPartitionEntry getCurrentPartitionEntry() {
        return currentPartition;
    }

    public void setCurrentGroup(SinglyRootedNodeEntry group) {
        currentGroup = group;
    }

    public SinglyRootedNodeEntry getSelectedGroup() {
        return currentGroup;
    }

    public Map<PartitionedNode, PartitionedNodeEntry> getContainerEntries() {
        return layout.getContainerEntries();
    }

    public Map<SinglyRootedNode, SinglyRootedNodeEntry> getNodeEntries() {
        return layout.getGroupEntries();
    }

    public int getEdgeWidth() {
        return EDGE_WIDTH;
    }

    /**
     * Populates the right-click menu for edges.
     */
    private void initializeEdgeMenu() {

        JMenuItem goToParentMenuItem = new JMenuItem("Go To Parent");
        goToParentMenuItem.addActionListener((e) -> {
            try {
                GenericInternalGraphFrame igf = AbstractionNetworkGraph.this.getParentInternalFrame();
                igf.focusOnComponent(getNodeEntries().get(selectedEdge.getTarget()));
            } catch (Exception exception) {
                System.err.println(exception);
            }
        });

        edgeMenu.add(goToParentMenuItem);

        JMenuItem goToChildMenuItem = new JMenuItem("Go To Child");
        goToChildMenuItem.addActionListener((e) -> {
            try {
                GenericInternalGraphFrame igf = AbstractionNetworkGraph.this.getParentInternalFrame();
                igf.focusOnComponent(getNodeEntries().get(selectedEdge.getSource()));
            } catch (Exception exception) {
                System.err.println(exception);
            }
        });

        edgeMenu.add(goToChildMenuItem);

        edgeMenu.add(new JPopupMenu.Separator());

        JMenuItem removeEdgeMenuItem = new JMenuItem("Delete Edge");

        removeEdgeMenuItem.addActionListener((e) -> {
            removeEdge(selectedEdge);
            redrawEdges();
        });

        edgeMenu.add(removeEdgeMenuItem);
    }

    public AbstractionNetworkGraphLayout getGraphLayout() {
        return layout;
    }
    
    public int getAbNWidth() {
        return layout.getAbNWidth();
    }
    
    public int getAbNHeight() {
        return layout.getAbNHeight();
    }
    
    public AbNLabelManager getLabelManager() {
        return labelManager;
    }
}
