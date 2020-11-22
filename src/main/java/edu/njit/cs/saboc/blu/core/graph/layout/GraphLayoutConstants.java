package edu.njit.cs.saboc.blu.core.graph.layout;

/**
 * A set of constants used when defining abstraction network graph layouts
 * 
 * @author Chris
 */
public abstract class GraphLayoutConstants {

    /**
     * Initial height of the space between each row of areas.
     */
    public static final int CONTAINER_ROW_HEIGHT = 60;
    /**
     * Initial width of the space between each area.
     */
    public static final int CONTAINER_CHANNEL_WIDTH = 40;
    /**
     * Initial width of the space between each pArea.
     */
    public static final int GROUP_CHANNEL_WIDTH = 10;
    /**
     * Initial height of the rows between each pArea
     */
    public static final int GROUP_ROW_HEIGHT = 10;
    /**
     * Initial width of the space between a region and the side of the pArea
     */
    public static final int PARTITION_CHANNEL_WIDTH = 10;
    /**
     * Initial width of the space between a region and the top of the pArea
     */
    public static final int PARTITION_ROW_HEIGHT = 45;
}
