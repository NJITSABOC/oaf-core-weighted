package edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.disjointabn;

import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointAbstractionNetwork;
import edu.njit.cs.saboc.blu.core.abn.disjoint.DisjointNode;
import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.abn.node.SinglyRootedNode;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.PartitionedAbNConfiguration;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.AbstractEntityList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.DisjointAbNOverlapMetrics;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeList;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.NodeOverlapMetrics;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.OverlappingDetailsEntryComparator;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingDetailsEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.entry.OverlappingNodeEntry;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionAdapter;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.listeners.EntitySelectionListener;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingDetailsTableModel;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.details.models.OverlappingNodeTableModel;
import edu.njit.cs.saboc.blu.core.gui.panels.abnderivationwizard.AbNDerivationWizardPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Chris O
 */
public class DisjointAbNSubsetSelectionPanel<T extends SinglyRootedNode> extends AbNDerivationWizardPanel {
    
    public interface DeriveDisjointAbNSubsetAction<T> {
        public void deriveSubsetDisjointAbN(Set<T> subset);
        public void deriveCompleteDisjointAbN();
    }
    
    private final Set<T> selectedOverlaps = new HashSet<>();

    private final AbstractEntityList<OverlappingNodeEntry> overlappingNodeTable;

    private final AbstractEntityList<OverlappingDetailsEntry> overlappingDetailsTable;

    private final NodeList<T> selectedSubsetList;
    
    private final JButton showFullDisjointTaxonomyBtn;
    
    private final JButton deriveBtn;

    private Optional<DisjointAbstractionNetwork> optCurrentDisjointAbN = Optional.empty();

    private Optional<DisjointAbNOverlapMetrics> optCurrentMetrics = Optional.empty();
    
    private final DeriveDisjointAbNSubsetAction<T> deriveDisjointAbNSubsetAction;

    public DisjointAbNSubsetSelectionPanel(
            PartitionedAbNConfiguration config, 
            DeriveDisjointAbNSubsetAction<T> deriveDisjointAbNSubsetAction) {

        this.deriveDisjointAbNSubsetAction = deriveDisjointAbNSubsetAction;
        
        this.setLayout(new BorderLayout());
        
        JPanel derivationOptionsPanel = new JPanel(new GridLayout(1, 3, 4, 4));

        overlappingNodeTable = new AbstractEntityList<OverlappingNodeEntry>(new OverlappingNodeTableModel(config)) {
            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingNodeEntry>> entities) {
                String base = String.format("Overlapping %s", config.getTextConfiguration().getNodeTypeName(true));

                if (entities.isPresent()) {
                    return String.format("%s (%d)", base, entities.get().size());
                } else {
                    return String.format("%s (0)", base);
                }
            }
        };

        overlappingNodeTable.addEntitySelectionListener(new EntitySelectionListener<OverlappingNodeEntry>() {

            @Override
            public void entityClicked(OverlappingNodeEntry entity) {

                selectedSubsetList.clearContents();
                selectedOverlaps.clear();

                selectedOverlaps.add((T) entity.getOverlappingNode());

                updateSelectedSubsetLists();

                if (optCurrentMetrics.isPresent()) {

                    NodeOverlapMetrics overlapMetrics = optCurrentMetrics.get().getGroupMetrics().get(entity.getOverlappingNode());

                    Set<DisjointNode> intersectionNodes = overlapMetrics.getAllIntersectionNodes();

                    HashMap<Node, Set<DisjointNode>> commonDisjointNodes = new HashMap<>();

                    intersectionNodes.forEach((group) -> {
                        Set<Node> overlappingGroups = group.getOverlaps();

                        for (Node overlappingGroup : overlappingGroups) {

                            if (!overlappingGroup.equals(entity.getOverlappingNode())) {

                                if (!commonDisjointNodes.containsKey(overlappingGroup)) {
                                    commonDisjointNodes.put(overlappingGroup, new HashSet<>());
                                }

                                commonDisjointNodes.get(overlappingGroup).add(group);
                            }
                        }
                    });

                    ArrayList<OverlappingDetailsEntry> entries = new ArrayList<>();

                    // TODO: Filter based on currently selected subset
                    commonDisjointNodes.forEach((node, disjointNodes) -> {
                        entries.add(new OverlappingDetailsEntry(node, disjointNodes));
                    });

                    Collections.sort(entries, new OverlappingDetailsEntryComparator());

                    overlappingDetailsTable.setContents(entries);
                }
            }

            @Override
            public void entityDoubleClicked(OverlappingNodeEntry entity) {
                entityClicked(entity);
            }

            @Override
            public void noEntitySelected() {
                overlappingDetailsTable.clearContents();
            }
        });

        this.overlappingDetailsTable = new AbstractEntityList<OverlappingDetailsEntry>(new OverlappingDetailsTableModel(config)) {

            @Override
            protected String getBorderText(Optional<ArrayList<OverlappingDetailsEntry>> entities) {

                if (entities.isPresent()) {
                    return String.format("Overlaps With (%d)", entities.get().size());
                } else {
                    return "Overlaps With (0)";
                }
            }
        };

        this.overlappingDetailsTable.addEntitySelectionListener(new EntitySelectionAdapter<OverlappingDetailsEntry>() {

            @Override
            public void entityDoubleClicked(OverlappingDetailsEntry entity) {
                selectedOverlaps.add((T) entity.getOverlappingNode());

                updateSelectedSubsetLists();
            }

        });

        this.selectedSubsetList = new NodeList<>(config);

        this.selectedSubsetList.addEntitySelectionListener(new EntitySelectionAdapter<T>() {

            @Override
            public void entityDoubleClicked(T entity) {
                selectedOverlaps.remove(entity);

                updateSelectedSubsetLists();
            }
        });

        derivationOptionsPanel.add(overlappingNodeTable);
        derivationOptionsPanel.add(overlappingDetailsTable);
        derivationOptionsPanel.add(selectedSubsetList);
        
        this.add(derivationOptionsPanel, BorderLayout.CENTER);
        
        this.showFullDisjointTaxonomyBtn = new JButton("Display Complete Disjoint " + config.getTextConfiguration().getAbNTypeName(false));
        this.showFullDisjointTaxonomyBtn.addActionListener( (ae) -> {
            this.deriveDisjointAbNSubsetAction.deriveCompleteDisjointAbN();
        });
        
        this.deriveBtn = new JButton("Display Subset");
        this.deriveBtn.addActionListener( (ae) -> {
            this.deriveDisjointAbNSubsetAction.deriveSubsetDisjointAbN(selectedOverlaps);
        });
        
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        southPanel.add(showFullDisjointTaxonomyBtn);
        southPanel.add(Box.createHorizontalStrut(150));
        southPanel.add(deriveBtn, BorderLayout.EAST);

        
        this.add(southPanel, BorderLayout.SOUTH);
        
        resetView();
    }

    public Set<T> getSelectedSubset() {
        return selectedOverlaps;
    }

    @Override
    public void setEnabled(boolean value) {
        super.setEnabled(value);
    }

    public void initialize(DisjointAbstractionNetwork disjointAbN) {
        this.optCurrentDisjointAbN = Optional.of(disjointAbN);
        this.optCurrentMetrics = Optional.of(new DisjointAbNOverlapMetrics(disjointAbN));

        ArrayList<Node> overlappingGroups = new ArrayList<>(disjointAbN.getOverlappingNodes());

        DisjointAbNOverlapMetrics metrics = this.optCurrentMetrics.get();

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

        this.overlappingNodeTable.setContents(entries);
    }

    @Override
    public void clearContents() {
        super.clearContents();

        this.overlappingNodeTable.clearContents();
        this.overlappingDetailsTable.clearContents();

        this.selectedSubsetList.clearContents();
    }

    @Override
    public final void resetView() {

    }

    private void updateSelectedSubsetLists() {

        ArrayList<T> sortedNodes = new ArrayList<>(selectedOverlaps);

        Collections.sort(sortedNodes, (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });

        selectedSubsetList.setContents(sortedNodes);
    }
}
