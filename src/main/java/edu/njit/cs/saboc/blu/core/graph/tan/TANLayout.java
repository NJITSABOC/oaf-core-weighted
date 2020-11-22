package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;

import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public class TANLayout<T extends ClusterTribalAbstractionNetwork> extends BaseTribalAbstractionNetworkLayout<T> {
    
    public TANLayout(AbstractionNetworkGraph<T> graph, T tan, TANConfiguration config) {
        super(graph, tan, config);
    }
    
    public void doLayout() {

        super.doLayout();
        
        AbstractionNetworkGraph graph = this.getGraph();

        Band lastSet = null;   // Used for generating the graph - this is the data version of an area
        BandEntry currentBandEntry = null;    // Used for generating the graph - this is the graphical representation of an area
        BandPartitionEntry currentPartitionEntry = null;
        
        GraphLevel currentLevel = null; // This is used as a temporary variable in this method to hold the current level.
        GraphGroupLevel currentClusterLevel = null; // Used for generating the graph

        ArrayList<Color> levelColors = BaseTribalAbstractionNetworkLayout.getBandLevelColors();

        int bandX = 0;  // The first area on each line is given an areaX value of 0.
        int bandY = 0;  // The first row of areas is given an areaY value of 0.
        int clusterX, clusterY;
        int x = 0, y = 20, width = 0, maxHeight = 0;

        addGraphLevel(new GraphLevel(0, graph, new ArrayList<>())); 

        for (Band band : super.getBandsInLayout()) { 
            BandEntry bandEntry;
            
            ArrayList<Cluster> clusters = new ArrayList<>(band.getClusters());
            clusters.sort((a,b) -> {
               
                if(a.getConceptCount() == b.getConceptCount()) {
                    return a.getRoot().getName().compareToIgnoreCase(b.getName());
                } else {
                    return b.getConceptCount() - a.getConceptCount();
                }
            });

            int maxRows, x2, y2, regionX, partitionBump;

            int[] bandClusterX;

            if (lastSet != null && lastSet.getPatriarchs().size() != band.getPatriarchs().size()) { // If a new row should be created...

                x = 0;  
                y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT; 

                bandY++;    // Update the areaY variable to reflect the new row.
                bandX = 0;  // Reset the areaX variable.

                maxHeight = 0;  // Reset the maxHeight variable since this is a new row.

                addGraphLevel(new GraphLevel(bandY, graph,
                        generateUpperRowLanes(-5, GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); // Add a level object to the arrayList in the dataGraph object
            }

            width = 0;
            maxRows = 0;

            int clusterCount = clusters.size();
            
            int clusterEntriesWide = (int) Math.ceil(Math.sqrt(clusterCount));

            int setWidth = clusterEntriesWide * (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);

            String bandName = band.getName();

            Set<Concept> concepts = band.getConcepts();
            
            int conceptCount = concepts.size();
            int clustersInBand = clusters.size();
            
            String conceptTypeName = super.getConfiguration().getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(conceptCount != 1);
            String clusterName = super.getConfiguration().getTextConfiguration().getNodeTypeName(clustersInBand != 1);
            
            String countString = String.format("(%d %s, %d %s)", conceptCount, conceptTypeName, clustersInBand, clusterName);

            bandName += (" " + countString);
            
            JLabel bandLabel;
            
            if(band.getPatriarchs().size() == 1) {
                bandLabel = new JLabel();
                bandLabel.setSize(1, 1);
            } else {
                bandLabel = createBandPartitionLabel(super.getTAN(), band.getPatriarchs(), countString, setWidth, true);
            }

            setWidth = Math.max(setWidth, bandLabel.getWidth() + 8);

            width += setWidth + 20;

            int height = bandLabel.getHeight() + (int) (Math.ceil((double) clusterCount / clusterEntriesWide))
                    * (SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT);  // Set the height to the greater of (a) the current height or (b) the number of regions in a column times the height of each pArea and the space between them.

            maxRows = Math.max(maxRows, (int) Math.ceil(Math.sqrt(clusterCount))); // Update the maxRows variable.

            width += 20;
            height += 60 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            // Keeps track of the tallest cell on a row so that it knows how much lower to position the next row to avoid overlap.
            if (height > maxHeight) {
                maxHeight = height;
            }

            currentLevel = getLevels().get(bandY);

            Color color = levelColors.get(band.getPatriarchs().size() % levelColors.size());

            bandEntry = createBandEntry(band, x, y, width, height, color, bandX, currentLevel); // Create the area

            getContainerEntries().put(band, bandEntry);

            currentLevel.addContainerEntry(bandEntry);    
            
            addColumn(bandX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); 

            currentBandEntry = (BandEntry)currentLevel.getContainerEntries().get(bandX);

            regionX = GraphLayoutConstants.PARTITION_CHANNEL_WIDTH;

            bandClusterX = new int[maxRows];
            partitionBump = 0;

            x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
            y2 = bandLabel.getHeight() + 30;
            
            clusterX = 0;
            clusterY = 0;
            
            int labelXPos =  GraphLayoutConstants.PARTITION_CHANNEL_WIDTH + (setWidth - bandLabel.getWidth()) / 2;
            bandLabel.setLocation(labelXPos, 4);

            BandPartitionEntry overlapPartition = createBandPartitionEntry(band, 
                    bandName, 
                    bandEntry,
                    regionX - partitionBump, 
                    10, 
                    setWidth + GraphLayoutConstants.GROUP_CHANNEL_WIDTH + 10,
                    height - 20, 
                    color, 
                    bandLabel);

            partitionBump++;
            currentPartitionEntry = (BandPartitionEntry)currentBandEntry.addPartitionEntry(overlapPartition);
            
            currentPartitionEntry.addGroupLevel(new GraphGroupLevel(0, currentPartitionEntry)); // Add a new pAreaLevel to the data representation of the current Area object.

            currentBandEntry.addRow(0, generateUpperRowLanes(-4,
                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentBandEntry));

            int i = 0;

            for (Cluster cluster : clusters) { // Draw the pArease inside this region
                ClusterEntry clusterEntry;

                currentClusterLevel = currentPartitionEntry.getGroupLevels().get(clusterY);

                clusterEntry = createClusterEntry(cluster, overlapPartition, x2, y2, clusterX, currentClusterLevel);

                overlapPartition.getVisibleGroups().add(clusterEntry);

                currentPartitionEntry.addColumn(bandClusterX[clusterY], generateColumnLanes(-3,
                        GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, currentBandEntry));

                getGroupEntries().put(cluster, clusterEntry);    // Store it in a map keyed by its ID...

                currentClusterLevel.addGroupEntry(clusterEntry);

                if ((i + 1) % clusterEntriesWide == 0 && i < clusters.size() - 1) {
                    y2 += SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                    x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    clusterX = 0;
                    bandClusterX[clusterY]++;
                    clusterY++;

                    if (currentPartitionEntry.getGroupLevels().size() <= clusterY) {
                        currentPartitionEntry.addGroupLevel(new GraphGroupLevel(clusterY, currentPartitionEntry)); // Add a new pAreaLevel to the data representation of the current Area object.
                        currentBandEntry.addRow(clusterY, generateUpperRowLanes(-4,
                                GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentBandEntry));
                    }
                } else {
                    x2 += (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                    clusterX++;
                    bandClusterX[clusterY]++;
                }

                i++;
            }

            x += width + 40;  // Set x to a position after the newly created area and the appropriate space after that given the set channel width.
            bandX++;
            lastSet = band;
        }
        
        this.centerGraphLevels(this.getGraphLevels());
        
        graph.setBounds(0, 0, graph.getAbNWidth(), graph.getAbNHeight());
    }
}
