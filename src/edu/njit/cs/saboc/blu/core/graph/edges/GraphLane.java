package edu.njit.cs.saboc.blu.core.graph.edges;

import edu.njit.cs.saboc.blu.core.graph.nodes.PartitionedNodeEntry;
import java.util.ArrayList;

/**
 * The data class representing the visual concept of a lane within a row/column
 * 
 * @author David Daudelin
 */
public class GraphLane {

    /**
     * The horizontal pixel position an edge running through this lane (if it's in a channel) should be set at.
     */
    private int posX;
    /**
     * The vertical pixel position an edge running through this lane (if it's in a row) should be set at.
     */
    private int posY;
    /**
     * The size of this lane.
     */
    private int size;
    /**
     * Indicates if an edge is already running through this lane.
     */
    boolean isEmpty;
    
    ArrayList<GraphEdge> edgesInLane;
    
    /**
     * Area that this lane is inside, if any.
     */
    PartitionedNodeEntry parent;

    public GraphLane(int x, int y, int s, PartitionedNodeEntry p) {
        posX = x;
        posY = y;
        size = s;
        isEmpty = true;
        parent = p;
        edgesInLane = new ArrayList<GraphEdge>();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public PartitionedNodeEntry getContainerEntryParent() {
        return parent;
    }

    /**
     * Sets whether or not this lane is empty.
     * @param state
     */
    public void setEmpty(boolean state) {
        isEmpty = state;
    }

    public void setPosX(int pX) {
        posX = pX;
    }

    public void setPosY(int pY) {
        posY = pY;
    }

    public void setSize(int s) {
        size = s;
    }

    @Override
    public String toString() {
        return "Lane: (" + posX + "," + posY + ") - Empty = " + isEmpty;
    }

    public ArrayList<GraphEdge> getEdgesInLane() {
        return edgesInLane;
    }
}
