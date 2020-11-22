package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.PartitionedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeCombinationsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingCombinationsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingDetailsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingNodeTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class DisjointAbNMetricsPanel<T extends PartitionedNode> extends BaseNodeInformationPanel<T> {
    
    private final AbstractEntityList<OverlappingNodeEntry> overlappingGroupTable;
    private final AbstractEntityList<OverlappingDetailsEntry> overlappingDetailsTable;
    private final AbstractEntityList<OverlappingNodeCombinationsEntry> overlappingCombinationsTable; 
    
    private final PartitionedAbNConfiguration configuration;
    
    private final JSplitPane splitPane;
    
    private Optional<DisjointAbNOverlapMetrics> currentMetrics = Optional.empty();
    
    public static enum PanelOrientation {
        Horizontal,
        Vertical
    }

    public DisjointAbNMetricsPanel(PartitionedAbNConfiguration configuration, PanelOrientation orientation) {

        this.configuration = configuration;
        
        this.setLayout(new BorderLayout());
        
        int split = orientation.equals(PanelOrientation.Horizontal) ? JSplitPane.HORIZONTAL_SPLIT : JSplitPane.VERTICAL_SPLIT;
        
        this.splitPane = NodeDetailsPanel.createStyledSplitPane(split);
        
        overlappingGroupTable = new AbstractEntityList<OverlappingNodeEntry>(new OverlappingNodeTableModel(configuration)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeEntry>> entities) {
                String base = String.format("Overlapping %s", configuration.getTextConfiguration().getNodeTypeName(true));
                
                if(entities.isPresent()) {
                    return String.format("%s (%d)", base, entities.get().size());
                } else {
                    return String.format("%s (0)", base);
                }
            }
        };
        
        overlappingGroupTable.addEntitySelectionListener(new EntitySelectionAdapter<OverlappingNodeEntry> () {

            @Override
            public void entityClicked(OverlappingNodeEntry entity) {
                if (currentMetrics.isPresent()) {
                    displayOverlappingDetailsFor(entity.getOverlappingNode());
                }
            }

            @Override
            public void noEntitySelected() {
                overlappingDetailsTable.clearContents();
            }
        });
        
        overlappingDetailsTable = new AbstractEntityList<OverlappingDetailsEntry>(new OverlappingDetailsTableModel(configuration)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingDetailsEntry>> entities) {
                
                if(entities.isPresent()) {
                    return String.format("Overlaps With (%d)", entities.get().size());
                } else {
                    return "Overlaps With (0)";
                }
            }
        };
        
        overlappingCombinationsTable = new AbstractEntityList<OverlappingNodeCombinationsEntry>(new OverlappingCombinationsTableModel(configuration)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeCombinationsEntry>> entities) {
                if(entities.isPresent()) {
                    return String.format("Overlapping Combinations (%d)", entities.get().size());
                } else {
                    return "Overlapping Combinations (0)";
                }
            }
        };
        
        JTabbedPane overlapDetailsTabs = new JTabbedPane();
        overlapDetailsTabs.addTab("Individual Overlaps", overlappingDetailsTable);
        overlapDetailsTabs.addTab("Combinations", overlappingCombinationsTable);
        
        splitPane.setTopComponent(overlappingGroupTable);
        splitPane.setBottomComponent(overlapDetailsTabs);
        
        this.add(splitPane);
    }
    
    private void displayOverlappingDetailsFor(Node selectedNode) {
        
        DisjointAbNOverlapMetrics metrics = currentMetrics.get();

        NodeOverlapMetrics overlapMetrics = metrics.getGroupMetrics().get(selectedNode);

        Set<DisjointNode> intersectionNodes = overlapMetrics.getAllIntersectionNodes();

        HashMap<Node, Set<DisjointNode>> commonDisjointGroups = new HashMap<>();

        for (DisjointNode group : intersectionNodes) {
            Set<Node> overlappingGroups = group.getOverlaps();

            for (Node overlappingGroup : overlappingGroups) {
                if (!overlappingGroup.equals(selectedNode)) {
                    if (!commonDisjointGroups.containsKey(overlappingGroup)) {
                        commonDisjointGroups.put(overlappingGroup, new HashSet<>());
                    }

                    commonDisjointGroups.get(overlappingGroup).add(group);
                }
            }
        }

        ArrayList<OverlappingDetailsEntry> entries = new ArrayList<>();

        commonDisjointGroups.forEach((node, disjointNodes) -> {
            entries.add(new OverlappingDetailsEntry(node, disjointNodes));
        });

        Collections.sort(entries, new OverlappingDetailsEntryComparator());

        overlappingDetailsTable.setContents(entries);
        
        HashMap<Set<Node>, Set<DisjointNode>> disjointGroupsByOverlap = new HashMap<>();
        
        intersectionNodes.forEach( (disjointNode) -> {
            if(!disjointGroupsByOverlap.containsKey(disjointNode.getOverlaps())) {
                disjointGroupsByOverlap.put(disjointNode.getOverlaps(), new HashSet<>());
            }
            
            disjointGroupsByOverlap.get(disjointNode.getOverlaps()).add(disjointNode);
        });

        ArrayList<OverlappingNodeCombinationsEntry> combinationEntries = new ArrayList<>();
        
        disjointGroupsByOverlap.forEach( (overlappingGroups, disjointNodeSet) -> {
            combinationEntries.add(new OverlappingNodeCombinationsEntry(selectedNode, disjointNodeSet));
        });
        
        Collections.sort(combinationEntries, new OverlappingNodeCombinationsComparator());
        
        overlappingCombinationsTable.setContents(combinationEntries);
    }
    
    @Override
    public void setContents(T partitionedNode) {

        splitPane.setDividerLocation(300);
        
        DisjointAbstractionNetwork disjointAbN = configuration.getDisjointAbstractionNetworkFor(partitionedNode);
        
        ArrayList<Node> overlappingGroups = new ArrayList<>(disjointAbN.getOverlappingNodes());
        
        currentMetrics = Optional.of(new DisjointAbNOverlapMetrics(disjointAbN));
        
        DisjointAbNOverlapMetrics metrics = currentMetrics.get();

        Collections.sort(overlappingGroups, (a, b) -> {
            int aOverlapCount = metrics.getGroupMetrics().get(a).getOverlappingConcepts().size();
            int bOverlapCount = metrics.getGroupMetrics().get(b).getOverlappingConcepts().size();
            
            if (aOverlapCount == bOverlapCount) {
                return a.getName().compareToIgnoreCase(b.getName());
            }
            
            return bOverlapCount - aOverlapCount;
        });
        
        ArrayList<OverlappingNodeEntry> entries = new ArrayList<>();
        
        overlappingGroups.forEach((overlappingNode) -> {
            entries.add(new OverlappingNodeEntry(overlappingNode, metrics.getGroupMetrics().get(overlappingNode).getOverlappingConcepts()));
        });
        
        overlappingGroupTable.setContents(entries);
    }

    @Override
    public void clearContents() {
        currentMetrics = Optional.empty();
        
        overlappingGroupTable.clearContents();
        
        overlappingDetailsTable.clearContents();
        overlappingCombinationsTable.clearContents();
    }
}
