package edu.njit.cs.saboc.blu.core.graph.tan;

import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.abn.tan.ClusterTribalAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.tan.Band;
import edu.njit.cs.saboc.blu.core.abn.tan.Cluster;
import edu.njit.cs.saboc.blu.core.graph.AbstractionNetworkGraph;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphGroupLevel;
import edu.njit.cs.saboc.blu.core.graph.edges.GraphLevel;
import edu.njit.cs.saboc.blu.core.graph.layout.AbstractionNetworkGraphLayout;
import edu.njit.cs.saboc.blu.core.graph.layout.GraphLayoutConstants;
import edu.njit.cs.saboc.blu.core.graph.nodes.EmptyContainerPartitionEntry;
import edu.njit.cs.saboc.blu.core.graph.nodes.SinglyRootedNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.tan.configuration.TANConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Chris O
 */
public abstract class BaseTribalAbstractionNetworkLayout<T extends ClusterTribalAbstractionNetwork> extends AbstractionNetworkGraphLayout<T> {

    private final T tan;

    private final TANConfiguration config;
    
    public static ArrayList<Color> getBandLevelColors() {
        // These are a set of styles such that each new row is given a different color.
        Color[] background = {
            Color.GRAY,
            new Color(250, 250, 250),
            new Color(55, 213, 102),
            new Color(121, 212, 250),
            new Color(242, 103, 103),
            new Color(232, 255, 114),
            Color.cyan,
            Color.orange,
            Color.pink,
            Color.green,
            Color.yellow
        };
        
        for(int c = 1; c < background.length; c++) {
            background[c] = background[c].brighter();
        }
        
        return new ArrayList<>(Arrays.asList(background));
    }

    public BaseTribalAbstractionNetworkLayout(AbstractionNetworkGraph<T> graph, T tan, TANConfiguration config) {
        super(graph);

        this.tan = tan;

        this.config = config;
    }

    public T getTAN() {
        return tan;
    }

    public TANConfiguration getConfiguration() {
        return config;
    }

    public void doLayout() {
        ArrayList<Band> sortedSets = new ArrayList<>();    // Used for generating the graph
        ArrayList<Band> levelSets = new ArrayList<>();     // Used for generating the graph

        ArrayList<Band> tempSets = new ArrayList<>(tan.getBands());

        Band lastSet = null;

        Collections.sort(tempSets, (a, b) -> a.getPatriarchs().size() - b.getPatriarchs().size());

        for (Band set : tempSets) {

            if (lastSet != null && lastSet.getPatriarchs().size() != set.getPatriarchs().size()) {
                Collections.sort(levelSets, (Band a, Band b) -> {
                    int aClusterSize = a.getClusters().size();
                    int bClusterSize = b.getClusters().size();
                    
                    if (aClusterSize == bClusterSize) {
                        return a.getConceptCount() - b.getConceptCount();
                    } else {
                        return aClusterSize - bClusterSize;
                    }
                } // Sort the areas based on the number of their relationships.
                );

                int c = 0;

                for (c = 0; c < levelSets.size(); c += 2) {
                    sortedSets.add(levelSets.get(c));
                }

                if (levelSets.size() % 2 == 0) {
                    c = levelSets.size() - 1;
                } else {
                    c = levelSets.size() - 2;
                }

                for (; c >= 1; c -= 2) {
                    sortedSets.add(levelSets.get(c));
                }

                levelSets.clear();
            }

            levelSets.add(set);

            lastSet = set;
        }

        Collections.sort(levelSets, (Band a, Band b) -> {
            int aClusterSize = a.getClusters().size();
            int bClusterSize = b.getClusters().size();
            
            if (aClusterSize == bClusterSize) {
                return a.getConceptCount() - b.getConceptCount();
            } else {
                return aClusterSize - bClusterSize;
            }
        });

        int c = 0;

        for (c = 0;
                c < levelSets.size();
                c += 2) {
            sortedSets.add(levelSets.get(c));
        }

        if (levelSets.size()
                % 2 == 0) {
            c = levelSets.size() - 1;
        } else {
            c = levelSets.size() - 2;
        }

        for (; c >= 1; c -= 2) {
            sortedSets.add(levelSets.get(c));
        }

        setLayoutBands(sortedSets);
    }
    
    private void setLayoutBands(ArrayList<Band> bands) {
        super.setLayoutGroupContainers((ArrayList<PartitionedNode>)(ArrayList<?>)bands);
    }

    public ArrayList<Band> getBands() {
        return (ArrayList<Band>)(ArrayList<?>)super.getLayoutContainers();
    }

    protected ClusterEntry createClusterEntry(Cluster p, BandPartitionEntry parent, int x, int y, int pAreaX, GraphGroupLevel clusterLevel) {
        
        ClusterEntry clusterEntry = new ClusterEntry(p, getGraph(), parent, pAreaX, clusterLevel, new ArrayList<>());

        //Make sure this panel dimensions will fit on the graph, stretch the graph if necessary
        getGraph().stretchGraphToFitPanel(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        //Setup the panel's dimensions, etc.
        clusterEntry.setBounds(x, y, SinglyRootedNodeEntry.ENTRY_WIDTH, SinglyRootedNodeEntry.ENTRY_HEIGHT);

        parent.add(clusterEntry, 0);

        return clusterEntry;
    }

    protected BandEntry createBandEntry(Band set, int x, int y, int width, int height, Color c, int areaX, GraphLevel parentLevel) {
        BandEntry bandEntry = new BandEntry(set, getGraph(), areaX, parentLevel, new Rectangle(x, y, width, height));

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        bandEntry.setBounds(x, y, width, height);
        bandEntry.setBackground(c);

        getGraph().add(bandEntry, 0);

        return bandEntry;
    }

    protected BandPartitionEntry createBandPartitionEntry(
            Band band,
            String bandName,
            BandEntry bandEntry, int x, int y, int width, int height, Color c, JLabel partitionLabel) {

        BandPartitionEntry partitionEntry = new BandPartitionEntry(band, bandName,
                width, height, getGraph(), bandEntry, c, partitionLabel);

        getGraph().stretchGraphToFitPanel(x, y, width, height);

        partitionEntry.setBounds(x, y, width, height);

        bandEntry.add(partitionEntry, 0);

        return partitionEntry;
    }

    public BandEntry getBand(int level, int setX) {
        return (BandEntry) getConainterAt(level, setX);
    }

    public EmptyContainerPartitionEntry getBandPartition(int level, int setX, int partitionX) {
        return (EmptyContainerPartitionEntry) getContainerPartitionAt(level, setX, partitionX);
    }

    public ClusterEntry getCluster(int level, int setX, int partitionX, int clusterY, int clusterX) {
        return (ClusterEntry) getGroupEntry(level, setX, partitionX, clusterY, clusterX);
    }

    protected JLabel createBandPartitionLabel(ClusterTribalAbstractionNetwork tan, Set<Concept> patriarchs, String countString, int width, boolean treatAsBand) {

        Canvas canvas = new Canvas();
        FontMetrics fontMetrics = canvas.getFontMetrics(new Font("SansSerif", Font.BOLD, 14));

        ArrayList<String> bandPatriarchLabels = new ArrayList<>();

        for (Concept patriarch : patriarchs) {
            bandPatriarchLabels.add(patriarch.getName());
        }

        Collections.sort(bandPatriarchLabels);

        int longestPatriarch = -1;

        for (String patriarchLabel : bandPatriarchLabels) {

            int relNameWidth = fontMetrics.stringWidth(patriarchLabel);

            if (relNameWidth > longestPatriarch) {
                longestPatriarch = relNameWidth;
            }
        }

        bandPatriarchLabels.add(countString);

        if (fontMetrics.stringWidth(countString) > longestPatriarch) {
            longestPatriarch = fontMetrics.stringWidth(countString);
        }

        if (patriarchs.size() > 1) {
            longestPatriarch += fontMetrics.charWidth(',');
        }

        if (treatAsBand) {
            longestPatriarch += fontMetrics.charWidth('+');
        }

        if (longestPatriarch > width) {
            width = longestPatriarch + 4;
        }

        return this.createFittedPartitionLabel(bandPatriarchLabels.toArray(new String[0]), width, fontMetrics);
    }

    public JLabel createBandLabel(Band band, int width) {

        Set<Concept> conceptsInPartition = new HashSet();

        band.getClusters().forEach( (cluster) -> {
            conceptsInPartition.addAll(cluster.getConcepts());
        });

        int clusterCount = band.getClusters().size();

        int conceptCount = conceptsInPartition.size();

        String conceptName = config.getTextConfiguration().getOntologyEntityNameConfiguration().getConceptTypeName(conceptCount != 1);

        String clusterName = config.getTextConfiguration().getNodeTypeName(clusterCount != 1);

        String countStr = String.format("(%d %s, %d %s)", conceptCount, conceptName, clusterCount, clusterName);

        // TODO: Currently we do not partition bands base on inheritance type, so last Arg is always true.
        return this.createBandPartitionLabel(tan, band.getPatriarchs(), countStr, width, true);
    }

    @Override
    public JLabel createPartitionLabel(PartitionedNode partition, int width) {
         return createBandLabel((Band)partition, width);
    }
   
    public void resetLayout() {
         ArrayList<Band> bands = this.getBands();
         
         ArrayList<ArrayList<Band>> bandsByLevel = new ArrayList<>();
         
         ArrayList<Band> level = new ArrayList<>();
         
         Band lastBand = null;
   
         for(Band band : bands) {
             if (lastBand != null && lastBand.getPatriarchs().size() != band.getPatriarchs().size()) {
                 bandsByLevel.add(level);
                 
                 level = new ArrayList<>();
             }
             
             lastBand = band;
             level.add(band);
         }
         
         bandsByLevel.add(level);
         
         int y = 40;
         
         for(ArrayList<Band> levelBands : bandsByLevel) {
             int maxHeight = 0;
             
             for(Band band : levelBands) {
                 BandEntry entry = (BandEntry)this.getContainerEntries().get(band);
                 
                 entry.setLocation(entry.getX(), y);
                 
                 if(entry.getHeight() > maxHeight) {
                     maxHeight = entry.getHeight();
                 }
             }
             
             y += maxHeight + GraphLayoutConstants.CONTAINER_ROW_HEIGHT;
         }
    }
    
    public ArrayList<Band> getBandsInLayout() {
        return (ArrayList<Band>)(ArrayList<?>)super.getLayoutContainers();
    }
    
    public Map<Band, BandEntry> getBandEntries() {
        return (Map<Band, BandEntry>)(Map<?,?>)super.getContainerEntries();
    }
    
    public Map<Cluster, ClusterEntry> getClusterEntries() {
        return (Map<Cluster, ClusterEntry>)(Map<?,?>)super.getGroupEntries();
    }
}
