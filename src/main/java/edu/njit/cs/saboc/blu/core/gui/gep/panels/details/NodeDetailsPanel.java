package edu.njit.cs.saboc.blu.core.gui.gep.panels.details;

import edu.njit.cs.saboc.blu.core.abn.node.Node;
import edu.njit.cs.saboc.blu.core.gui.gep.panels.configuration.AbNConfiguration;
import edu.njit.cs.saboc.blu.core.ontology.Concept;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

/**
 *
 * @author Chris O
 * @param <T>
 */
public class NodeDetailsPanel<T extends Node> extends BaseNodeInformationPanel<T> {
    
    private final NodeSummaryPanel nodeSummaryPanel;

    private final NodeOptionsPanel nodeOptionsMenuPanel;

    private final NodeConceptList nodeConceptList;

    private final JSplitPane splitPane;
    
    private Optional<T> currentNode = Optional.empty();

    public NodeDetailsPanel(
            NodeSummaryPanel nodeSummaryPanel, 
            NodeOptionsPanel nodeOptionsMenuPanel, 
            NodeConceptList nodeConceptList,
            AbNConfiguration config) {
        
        this.nodeSummaryPanel = nodeSummaryPanel;
        this.nodeOptionsMenuPanel = nodeOptionsMenuPanel;
        this.nodeConceptList = nodeConceptList;

        getConceptList().addEntitySelectionListener(config.getUIConfiguration().getListenerConfiguration().getGroupConceptListListener());

        this.setLayout(new BorderLayout());

        this.splitPane = NodeDetailsPanel.createStyledSplitPane(JSplitPane.VERTICAL_SPLIT);
        
        JPanel upperPanel = new JPanel(new BorderLayout());
        
        upperPanel.add(nodeSummaryPanel, BorderLayout.CENTER);
        upperPanel.add(nodeOptionsMenuPanel, BorderLayout.SOUTH);
        
        splitPane.setTopComponent(upperPanel);
        splitPane.setBottomComponent(nodeConceptList);
        splitPane.setDividerLocation(400);

        this.add(splitPane, BorderLayout.CENTER);
    }
    
    protected final AbstractEntityList<Concept> getConceptList() {
        return nodeConceptList;
    }
    
    @Override
    public void setContents(T node) {
        this.currentNode = Optional.of(node);

        nodeSummaryPanel.setContents(node);
        nodeOptionsMenuPanel.setContents(node);
        
        nodeConceptList.setCurrentNode(node);
        
        ArrayList<Concept> sortedConcepts = new ArrayList<>(node.getConcepts());
        
        sortedConcepts.sort( (a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        
        nodeConceptList.setContents(sortedConcepts);
    }
    
    public void clearContents() {
        this.currentNode = Optional.empty();

        nodeSummaryPanel.clearContents();
        nodeOptionsMenuPanel.clearContents();
        
        nodeConceptList.clearCurrentNode();
        nodeConceptList.clearContents();
    }
    
    public static JSplitPane createStyledSplitPane(int alignment) {
        JSplitPane splitPane = new JSplitPane(alignment);
        splitPane.setBorder(null);

        Optional<BasicSplitPaneDivider> divider = Optional.empty();

        for (Component c : splitPane.getComponents()) {
            if (c instanceof BasicSplitPaneDivider) {
                divider = Optional.of((BasicSplitPaneDivider) c);
                break;
            }
        }

        if (divider.isPresent()) {
            divider.get().setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
            divider.get().setDividerSize(4);
        }
        
        return splitPane;
    }
    
    public Optional<T> getCurrentNode() {
        return currentNode;
    }
}
