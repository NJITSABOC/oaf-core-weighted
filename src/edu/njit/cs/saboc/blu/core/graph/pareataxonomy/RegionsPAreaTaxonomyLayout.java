package edu.njit.cs.saboc.blu.core.graph.pareataxonomy;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Area;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.InheritableProperty.InheritanceType;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PArea;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.PAreaTaxonomy;
import edu.njit.cs.saboc.blu.core.abn.pareataxonomy.Region;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.pareataxonomy.configuration.PAreaTaxonomyConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class RegionsPAreaTaxonomyLayout<T extends PAreaTaxonomy<? extends PArea>> extends BasePAreaTaxonomyLayout<T> {
    
    private final PAreaTaxonomyConfiguration config;

    public RegionsPAreaTaxonomyLayout(
            PAreaTaxonomyGraph graph, 
            T taxonomy, 
            PAreaTaxonomyConfiguration config) {
        
        super(graph, taxonomy);
        
        this.config = config;
    }

    @Override
    public void doLayout() {
        super.doLayout();

        Area lastArea = null;   // Used for generating the graph - this is the data version of an area
        AreaEntry currentArea = null;    // Used for generating the graph - this is the graphical representation of an area
        RegionEntry currentRegion = null;
        GraphLevel currentLevel = null; // This is used as a temporary variable in this method to hold the current level.
        GraphGroupLevel currentPAreaLevel = null; // Used for generating the graph

        // These are a set of styles such that each new row is given a different color.
        ArrayList<Color> backgrounds = BasePAreaTaxonomyLayout.getTaxonomyLevelColors();

        int areaX = 0;  // The first area on each line is given an areaX value of 0.
        int areaY = 0;  // The first row of areas is given an areaY value of 0.
        int pAreaX, pAreaY;
        int x = 0, y = 20, maxHeight = 0;

        currentLevel = null; // This is used as a temporary variable in this method to hold the current level.
        currentArea = null;
        currentRegion = null;
        currentPAreaLevel = null;

        addGraphLevel(new GraphLevel(0, getGraph(), new ArrayList<>())); // Add the first level of areas (the single pArea 0-relationship level) to the data representation of the graph.

        HashMap<Region, JLabel> regionLabels = new HashMap<>();

        for (Area area : super.getAreasInLayout()) {  // Loop through the areas and generate the diagram for each of them
            int x2; 
            int y2;
            int regionX;
            int regionBump;

            int[] areaPAreaX;

            if (lastArea != null && lastArea.getRelationships().size() != area.getRelationships().size()) { // If a new row should be created...
                // Reset the x coordinate to the left
                x = 0;  
                
                // Add the height of the tallest area to the y coordinate plus the areaRowHeight variable which defines how
                // much space should be between rows of areas.
                y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT; 

                areaY++;    // Update the areaY variable to reflect the new row.
                areaX = 0;  // Reset the areaX variable.

                maxHeight = 0;  // Reset the maxHeight variable since this is a new row.

                addGraphLevel(new GraphLevel(areaY, getGraph(),
                        generateUpperRowLanes(-5, GraphLayoutConstants.CONTAINER_ROW_HEIGHT - 7, 3, null))); // Add a level object to the arrayList in the dataGraph object
            }

            int areaWidth = 0;
            int areaHeight = 0;
            int maxRows = 0;
            
            Set<Region> regions = area.getRegions();

            for (Region region : regions) { // Loop through regions to calculate the necessary size for the area.
                int pareaCount = region.getPAreas().size();

                // Take the number of cells and find the square root of it (rounded up) to
                // find the minimum width required for a square that could hold all the pAreas.
                int pareasWide = (int) Math.ceil(Math.sqrt(pareaCount)); 

                int regionWidth = pareasWide * (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
             
                JLabel regionLabel = this.createRegionLabel(region, regionWidth);

                regionLabels.put(region, regionLabel);
                
                regionWidth = Math.max(regionWidth, regionLabel.getWidth() + 4);

                areaWidth += regionWidth + 20;
                
                // Set the height to the greater of (a) the current height or (b) the number of regions in a column times the height of each pArea and the space between them.
                areaHeight = Math.max(areaHeight, 
                        regionLabel.getHeight() + (int) (Math.ceil((double) pareaCount / pareasWide))
                        * (SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT));  

                maxRows = Math.max(maxRows, (int) Math.ceil(Math.sqrt(pareaCount))); // Update the maxRows variable.
            }

            areaWidth += 20;
            areaHeight += 60 + GraphLayoutConstants.GROUP_ROW_HEIGHT;

            if (areaHeight > maxHeight) // Keeps track of the tallest cell on a row so that it knows how much lower to position the next row to avoid overlap.
            {
                maxHeight = areaHeight;
            }

            currentLevel = getLevels().get(areaY);

            Color color = backgrounds.get(area.getRelationships().size() % backgrounds.size());

            AreaEntry areaEntry = createAreaPanel(getGraph(), area, x, y, areaWidth, areaHeight, color, areaX, currentLevel); // Create the area

            getAreaEntries().put(area, areaEntry);

            currentLevel.addContainerEntry(areaEntry);    // Add a data representation for this new area to the current area Level obj.
            
            addColumn(areaX, currentLevel.getLevelY(), generateColumnLanes(-3,
                    GraphLayoutConstants.CONTAINER_CHANNEL_WIDTH - 5, 3, null)); // Generates a column of lanes to the left of this area.

            currentArea = (AreaEntry)currentLevel.getContainerEntries().get(areaX);

            regionX = GraphLayoutConstants.PARTITION_CHANNEL_WIDTH;
            
            areaPAreaX = new int[maxRows];
            regionBump = 0;

            for (Region region : regions) { // Create the regions inside the newly created area.

                JLabel regionLabel = regionLabels.get(region);

                int pareaCount = region.getConceptCount();

                x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                y2 = regionLabel.getHeight() + 30;

                pAreaX = 0;
                pAreaY = 0;

                int horizontalPAreas = (int) Math.ceil(Math.sqrt(pareaCount));
                int regionWidth = horizontalPAreas * (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                
                regionWidth = Math.max(regionWidth, regionLabel.getWidth() + 4);
                
                int labelXPos =  GraphLayoutConstants.PARTITION_CHANNEL_WIDTH + (regionWidth - regionLabel.getWidth()) / 2;
                regionLabel.setLocation(labelXPos, 4);
                
                RegionEntry regionEntry = createRegionPanel(getGraph(), 
                        region, 
                        areaEntry,
                        regionX - regionBump, 
                        10, 
                        regionWidth + GraphLayoutConstants.GROUP_CHANNEL_WIDTH + 10,
                        areaHeight - 20, 
                        color, 
                        regionLabel);

                regionBump++;
                
                currentArea.addRegionEntry(regionEntry);
                
                currentRegion = regionEntry;
                
                currentRegion.addGroupLevel(new GraphGroupLevel(0, currentRegion)); // Add a new pAreaLevel to the data representation of the current Area object.
                
                currentArea.addRow(0, generateUpperRowLanes(-4,
                        GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentArea));

                int i = 0;

                for (PArea parea : region.getPAreas()) { // Draw the pArease inside this region

                    currentPAreaLevel = currentRegion.getGroupLevels().get(pAreaY);

                    PAreaEntry pareaPanel = createPAreaPanel(getGraph(), parea, regionEntry, x2, y2, pAreaX, currentPAreaLevel);

                    regionEntry.getVisibleGroups().add(pareaPanel);

                    currentRegion.addColumn(areaPAreaX[pAreaY], generateColumnLanes(-3,
                            GraphLayoutConstants.GROUP_CHANNEL_WIDTH - 2, 3, currentArea));

                    getGroupEntries().put(parea, pareaPanel);    // Store it in a map keyed by its ID...

                    currentPAreaLevel.addGroupEntry(pareaPanel);

                    if ((i + 1) % horizontalPAreas == 0 && i < pareaCount - 1) {
                        y2 += SinglyRootedNodeEntry.ENTRY_HEIGHT + GraphLayoutConstants.GROUP_ROW_HEIGHT;
                        x2 = (int) (1.5 * GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                        pAreaX = 0;
                        areaPAreaX[pAreaY]++;
                        pAreaY++;

                        if (currentRegion.getGroupLevels().size() <= pAreaY) {
                            currentRegion.addGroupLevel(new GraphGroupLevel(pAreaY, currentRegion)); // Add a new pAreaLevel to the data representation of the current Area object.
                            currentArea.addRow(pAreaY, generateUpperRowLanes(-4,
                                    GraphLayoutConstants.GROUP_ROW_HEIGHT - 5, 3, currentArea));
                        }
                    } else {
                        x2 += (SinglyRootedNodeEntry.ENTRY_WIDTH + GraphLayoutConstants.GROUP_CHANNEL_WIDTH);
                        pAreaX++;
                        areaPAreaX[pAreaY]++;
                    }

                    i++;
                }

                regionX += regionWidth + 20;
            }

            x += areaWidth + 40;  // Set x to a position after the newly created area and the appropriate space after that given the set channel width.
            areaX++;
            lastArea = area;
        }
        
        this.centerGraphLevels(this.getGraphLevels());
        
    }
    
    private JLabel createRegionLabel(Region region,int width) {
        String pareaStr;

        if (region.getPAreas().size() == 1) {
            pareaStr = "1 Partial-area";
        } else {
            pareaStr = String.format("%d Partial-areas", region.getPAreas().size());
        }

        String countStr;

        Set<Concept> concepts = region.getConcepts();

        String conceptStr;

        if (concepts.size() == 1) {
            conceptStr = String.format("1 %s", 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(false));
        } else {
            conceptStr = String.format("%d %s", concepts.size(), 
                    config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(true));
        }

        countStr = String.format("(%s, %s)", conceptStr, pareaStr);

        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(new Font("SansSerif", Font.BOLD, 14));
        
        Set<InheritableProperty> properties = region.getRelationships();

        String[] entries;

        if (properties.isEmpty()) {
            entries = new String[]{"\u2205", countStr};
        } else {
            entries = new String[properties.size()];

            int c = 0;

            int longestRelNameWidth = -1;

            for (InheritableProperty rel : properties) {
                
                String relName;
                
                if(rel.getInheritanceType() == InheritanceType.Inherited) {
                    relName = rel.getName() + "*";
                } else {
                    relName = rel.getName() + "+";
                }

                int relNameWidth = fontMetrics.stringWidth(relName);

                if (relNameWidth > longestRelNameWidth) {
                    longestRelNameWidth = relNameWidth;
                }

                entries[c++] = relName;
            }

            Arrays.sort(entries);

            entries = Arrays.copyOf(entries, entries.length + 1);

            entries[entries.length - 1] = countStr;

            if (fontMetrics.stringWidth(countStr) > longestRelNameWidth) {
                longestRelNameWidth = fontMetrics.stringWidth(countStr);
            }

            if (properties.size() > 1) {
                longestRelNameWidth += fontMetrics.charWidth(',');
            }

            if (longestRelNameWidth > width) {
                width = longestRelNameWidth + 4;
            }
        }

        return this.createFittedPartitionLabel(entries, width, fontMetrics);
    }
    
        
    @Override
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
        return createRegionLabel((Region)partition, width);
    }
}